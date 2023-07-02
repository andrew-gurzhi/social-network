package ru.otus.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.socialnetwork.service.UserService;
import ru.otus.socialnetwork.storage.model.UserEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@Validated
public class FriendController {

    private final UserService userService;

    @PostMapping(value = "/set/{userId}", produces = APPLICATION_JSON_VALUE)
    @Operation(description = "Добавление друга")
    public ResponseEntity<Void> setFriend(@PathVariable String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.setFriend(((UserEntity) authentication.getPrincipal()).getId(), userId);
        return ResponseEntity.ok().build();
    }

}
