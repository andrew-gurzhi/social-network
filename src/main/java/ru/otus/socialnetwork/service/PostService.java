package ru.otus.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.socialnetwork.mapper.PostMapper;
import ru.otus.socialnetwork.model.PostDto;
import ru.otus.socialnetwork.queueadapter.PostQueueAdapter;
import ru.otus.socialnetwork.storage.model.PostEntity;
import ru.otus.socialnetwork.storage.model.UserEntity;
import ru.otus.socialnetwork.storage.repository.PostRepository;
import ru.otus.socialnetwork.storage.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
//Сервис для работы с постами
public class PostService {
    public static final int PAGE = 0;
    public static final int PAGE_SIZE = 20;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final PostQueueAdapter postQueueAdapter;

    private final ManualCacheManager manualCacheManager;


    /**
     * Создание поста
     *
     * @param postText = текст поста
     * @param userId   = идентификатор пользователя сделавшего запрос
     */
    @Transactional
    public String createPost(String postText, UserEntity userId) {
        var post = new PostEntity();
        post.setId(UUID.randomUUID().toString());
        post.setText(postText);
        post.setUserId(userId);
        var postId =  postRepository.save(post).getId();
        sendPostEventToQueue(userId.getId(), postText);
        return postId;
    }

    /**
     * Получение списка постов друзей
     * Значение кешируется, если ответ нулл, а не пустая коллекция, значит значение еще не успелось записаться в кеш
     *
     * @param userId = идентификатор пользователя сделавшего запрос
     * @param offset оффсет
     * @param limit  лимит для пагинации
     * @return список постов
     */
    @Cacheable(value = "feedCache", key = "#userId")
    public List<PostDto> feed(String userId, Integer offset, Integer limit) {
        return fetchFeed(userId, offset, limit);
    }

    /**
     * Отпрака сообщения об создании поста в очередь
     * @param userId индентификатор пользователя сделавшего пост
     * @param postText текст поста
     */
    public void sendPostEventToQueue(String userId, String postText) {
        postQueueAdapter.sendPostToFriends(userId, postText);
    }

    /**
     * Получаем ленту постов из кеша, если нет, то ставим задачу на  получение в очередь
     *
     * @param userId = идентификатор пользователя сделавшего запрос
     * @param offset оффсет
     * @param limit  лимит для пагинации
     * @return лента постов
     */
    private List<PostDto> fetchFeed(String userId, Integer offset, Integer limit) {
        List<PostDto> cachedPosts = manualCacheManager.getPostsFromCache(userId, offset, limit);
        if (cachedPosts != null) {
            return cachedPosts;
        }
        return getFeed(userId);
    }

    /**
     * Получение ленты постов из базы данных
     *
     * @param userId = идентификатор пользователя сделавшего запрос
     * @return лента постов
     */
    @Transactional(readOnly = true)
    public List<PostDto> getFeed(String userId) {
        var friendIds = userRepository.findById(userId).orElseThrow().getFriends().stream().map(UserEntity::getId).toList();
        return postRepository.findAllByUserIds(friendIds, PageRequest.of(PAGE, PAGE_SIZE)).stream().map(postMapper::map).toList();
    }
}
