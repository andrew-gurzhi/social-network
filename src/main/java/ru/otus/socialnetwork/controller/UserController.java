package ru.otus.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.socialnetwork.model.UserDto;
import ru.otus.socialnetwork.service.UserService;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register", produces = APPLICATION_JSON_VALUE)
    @Operation(description = "Регистрация нового пользователя")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto user) {
        var createdUserId = userService.createUser(user);
        return ResponseEntity.ok().body(createdUserId);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") String id) {
        UserDto user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String firstName,
                                                    @RequestParam String secondName) {
        List<UserDto> user = userService.searchUser(firstName, secondName);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else return ResponseEntity.notFound().build();
    }
}
