package ru.otus.socialnetwork.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.otus.socialnetwork.model.PostDto;
import ru.otus.socialnetwork.service.ManualCacheManager;
import ru.otus.socialnetwork.service.PostService;

@Component
@RequiredArgsConstructor
public class PostListener {
    private final PostService postService;
    private final ManualCacheManager cacheManager;

    @KafkaListener(topics = "${application.post-feed-topic}", groupId = "my-group")
    public void onMessage(PostDto message) {
        // Обработка полученного сообщения
        System.out.println("Received message: " + message);
        var feed = postService.getFeed(message.getUserId());
        cacheManager.putPostsToCache(message.getUserId(), feed);
    }

}

