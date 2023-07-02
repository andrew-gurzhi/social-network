package ru.otus.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
//Пост пользователя
public class PostDto {
    // Идентификатор поста
    @JsonProperty("postId")
    private String id;
    // Текст сообщения
    @JsonProperty("postText")
    private String text;
    // Идентификатор пользователя
    @JsonProperty("userId")
    private String userId;
}
