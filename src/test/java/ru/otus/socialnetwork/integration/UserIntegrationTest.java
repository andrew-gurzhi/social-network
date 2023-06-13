package ru.otus.socialnetwork.integration;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.socialnetwork.model.Sex;
import ru.otus.socialnetwork.model.UserDto;
import ru.otus.socialnetwork.service.UserService;
import ru.otus.socialnetwork.testcontainer.AbstractContainerBaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserIntegrationTest extends AbstractContainerBaseTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testGetUserAuthError() {
        // Делаем запрос на получение пользователя, непройдена авторизация
        ResponseEntity<UserDto> response = restTemplate.getForEntity("/users?id=" + 1, UserDto.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    }

    @Test
    public void testGetUserSuccess() {
        // создаем тестового пользователя
        UserDto user = new UserDto();
        user.setFirstName("Jennifer");
        user.setSecondName("Donn");
        user.setAge(18);
        user.setPassword("pass");
        user.setCity("St. Petersburg");
        user.setSex(Sex.FEMALE);
        String createdUserId = userService.createUser(user);

        // отправляем POST запрос на получение токена аутентификации
        String token = restTemplate.postForEntity(
                "/login?id=" + createdUserId + "&password=" + user.getPassword(), null, String.class).getBody();

        // отправляем GET запрос на получение пользователя по id с токеном
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<UserDto> response = restTemplate.exchange("/users?id=" + createdUserId, HttpMethod.GET, entity, UserDto.class);

        // проверяем, что пользователь получен успешно
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // проверяем, что полученный пользователь соответствует тестовому пользователю
        UserDto retrievedUser = response.getBody();
        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
        assertEquals(user.getCity(), retrievedUser.getCity());
        assertEquals(user.getSex(), retrievedUser.getSex());
        assertNull(retrievedUser.getPassword());
    }
}
