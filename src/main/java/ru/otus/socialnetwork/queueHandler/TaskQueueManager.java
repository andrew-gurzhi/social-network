package ru.otus.socialnetwork.queueHandler;

import org.springframework.stereotype.Component;
import ru.otus.socialnetwork.model.FeedTask;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
 // Менеджер выполнения задач
public class TaskQueueManager {
    private final Queue<FeedTask> queueForFeedTask;

    public TaskQueueManager() {
        this.queueForFeedTask = new ConcurrentLinkedQueue<>();
    }

    public void addToFeedQueue(String userId, Integer offset, Integer limit) {
        queueForFeedTask.add(new FeedTask(userId, offset, limit));
    }

    public FeedTask dequeueFeedTask() {
        return queueForFeedTask.poll();
    }

    public boolean isTaskQueueEmpty() {
        return queueForFeedTask.isEmpty();
    }
}