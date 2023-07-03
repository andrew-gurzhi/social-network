create TABLE posts
(
    id                  VARCHAR(255)  PRIMARY KEY ,
    text         VARCHAR(255) NOT NULL,
    user_id         VARCHAR(255) NOT NULL
        constraint fk_posts_user_id references users
);