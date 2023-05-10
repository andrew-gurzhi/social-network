package ru.otus.socialnetwork.testcontainer;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractContainerBaseTest {

    static final SnPostgreSQLContainer postgreSQLContainer = new SnPostgreSQLContainer();

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", () -> "postgres");
        dynamicPropertyRegistry.add("spring.datasource.password", () -> "postgres");

    }
}

