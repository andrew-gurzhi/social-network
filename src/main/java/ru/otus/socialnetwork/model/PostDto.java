package ru.otus.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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

    @JsonCreator
    public PostDto(@JsonProperty("postId") String postId,
                   @JsonProperty("postText") String postText,
                   @JsonProperty("userId") String userId) {
        this.id = postId;
        this.text = postText;
        this.userId = userId;
    }
}
