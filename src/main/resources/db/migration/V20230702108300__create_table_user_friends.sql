CREATE TABLE user_friends (
                              user_id VARCHAR(255) NOT NULL,
                              friend_id VARCHAR(255) NOT NULL,
                              PRIMARY KEY (user_id, friend_id),
                              FOREIGN KEY (user_id) REFERENCES users (id),
                              FOREIGN KEY (friend_id) REFERENCES users (id)
);