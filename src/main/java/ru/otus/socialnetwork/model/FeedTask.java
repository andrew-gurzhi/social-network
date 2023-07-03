package ru.otus.socialnetwork.model;

import lombok.Data;

@Data
public class FeedTask {
    private final String userId;
    private final Integer offset;
    private final Integer limit;

}