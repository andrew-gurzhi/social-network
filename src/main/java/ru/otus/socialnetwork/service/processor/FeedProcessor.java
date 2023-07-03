package ru.otus.socialnetwork.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.otus.socialnetwork.model.FeedTask;
import ru.otus.socialnetwork.queueHandler.TaskQueueManager;
import ru.otus.socialnetwork.service.ManualCacheManager;
import ru.otus.socialnetwork.service.PostService;

@Component
@RequiredArgsConstructor
public class FeedProcessor {
    private final TaskQueueManager taskQueueManager;
    private final PostService postService;
    private final ManualCacheManager cacheManager;

    @Scheduled(fixedDelay = 100) // Выполнять каждую секунду
    public void processFeedQueue() {
        if (!taskQueueManager.isTaskQueueEmpty()) {
            FeedTask feedTask = taskQueueManager.dequeueFeedTask();
            if (feedTask != null) {
                String userId = feedTask.getUserId();
                Integer offset = feedTask.getOffset();
                Integer limit = feedTask.getLimit();
                var feed = postService.getFeed(userId, offset, limit);
                cacheManager.putPostsToCache(userId, offset, limit, feed);
            }
        }
    }
}