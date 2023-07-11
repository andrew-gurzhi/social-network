package ru.otus.socialnetwork.service;

import org.springframework.stereotype.Component;
import ru.otus.socialnetwork.model.PostDto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ManualCacheManager {
    public static final int OFFSET = 0;
    public static final int LIMIT = 20;
    private final Map<String, Map<String, List<PostDto>>> cache;

    public ManualCacheManager() {
        this.cache = new ConcurrentHashMap<>();
    }

    public void putPostsToCache(String userId, List<PostDto> posts) {
        String cacheKey = getCacheKey(userId, OFFSET, LIMIT);
        Map<String, List<PostDto>> userCache = cache.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        userCache.put(cacheKey, posts);
    }

    public List<PostDto> getPostsFromCache(String userId, Integer offset, Integer limit) {
        Map<String, List<PostDto>> userCache = cache.get(userId);
        if (userCache != null) {
            String cacheKey = getCacheKey(userId, offset, limit);
            return userCache.get(cacheKey);
        }
        return null;
    }

    private String getCacheKey(String userId, Integer offset, Integer limit) {
        return userId + "-" + offset + "-" + limit;
    }
}