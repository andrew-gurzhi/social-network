create TABLE users
(
    id                  VARCHAR(255)  PRIMARY KEY ,
    first_name         VARCHAR(255) NOT NULL,
    second_name         VARCHAR(255) NOT NULL,
    age     INT,
    sex            VARCHAR(128),
    city             VARCHAR(255),
    hobbies VARCHAR(512),
    uri_pages      VARCHAR(255),
    password VARCHAR(512)
);
