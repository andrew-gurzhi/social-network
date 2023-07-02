package ru.otus.socialnetwork.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "posts")
@Entity
@Getter
@Setter
public class PostEntity {
    @Id
    private String id;

    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userId;
}
