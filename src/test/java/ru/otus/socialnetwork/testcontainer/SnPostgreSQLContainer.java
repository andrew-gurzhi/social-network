package ru.otus.socialnetwork.testcontainer;

import org.testcontainers.containers.PostgreSQLContainer;

import static org.testcontainers.utility.DockerImageName.parse;

public class SnPostgreSQLContainer extends PostgreSQLContainer<SnPostgreSQLContainer> {

    public static final String IMAGE_NAME = "postgres:latest";

    public SnPostgreSQLContainer() {
        super(parse(IMAGE_NAME).asCompatibleSubstituteFor("postgres"));

        withDatabaseName("postgres");
        withUsername("postgres");
        withPassword("postgres");
    }
}
