package ru.otus.socialnetwork.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    private String id;
    // Имя
    private String firstName;
    // Фамилия
    private String secondName;
    // Возраст
    private Integer age;
    // Пол
    @Enumerated(EnumType.STRING)
    private Sex sex;
    // Город
    private String city;
    // Интересы
    private String hobbies;
    // Страницы с анкетой
    private String uriPages;
    // Пароль
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<UserEntity> friends;
}


