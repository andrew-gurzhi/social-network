package ru.otus.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {
    // Имя
    @JsonProperty("first_name")
    private String firstName;
    // Фамилия
    @JsonProperty("second_name")
    private String secondName;
    // Возраст
    @JsonProperty("age")
    private Integer age;
    // Пол
    @JsonProperty("sex")
    private Sex sex;
    // Город
    @JsonProperty("city")
    private String city;

    // Интересы
    @JsonProperty("hobbies")
    private String hobbies;
    // Страницы с анкетой
    @JsonProperty("uriPages")
    private String uriPages;

    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
