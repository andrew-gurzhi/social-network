package ru.otus.socialnetwork.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.socialnetwork.model.Sex;
import ru.otus.socialnetwork.model.UserDto;
import ru.otus.socialnetwork.service.UserService;
import ru.otus.socialnetwork.testcontainer.AbstractContainerBaseTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRegistrationIntegrationTest extends AbstractContainerBaseTest {
    @Autowired
    private UserService userService;
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    void testRegisterUser() {
        // создаем тестового пользователя
        UserDto user = new UserDto();
        user.setFirstName("John");
        user.setSecondName("Bolkowitch");
        user.setAge(22);
        user.setPassword("testpassword");
        user.setCity("Moscow");
        user.setSex(Sex.MALE);
        // Мок на энкодер делаем потому что пароль в json модели только на запись
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        // отправляем POST запрос на создание пользователя
        ResponseEntity<String> response = restTemplate.postForEntity("/users/register", user, String.class);

        // проверяем, что пользователь создан успешно
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // проверяем, что созданный пользователь соответствует тестовому пользователю
        String createdUserId = response.getBody();
        UserDto createdUser = userService.getUserById(createdUserId);
        assertEquals(user.getFirstName(), createdUser.getFirstName());
        assertEquals(user.getCity(), createdUser.getCity());
        assertEquals(user.getSex(), createdUser.getSex());
        assertNull(createdUser.getPassword());

    }
}