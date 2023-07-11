package ru.otus.socialnetwork.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.socialnetwork.model.PostDto;
import ru.otus.socialnetwork.service.PostService;
import ru.otus.socialnetwork.storage.model.UserEntity;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE)
    @Operation(description = "Добавление поста пользователя")
    public ResponseEntity<String> createPost(@Valid @RequestParam String postText) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = ((UserEntity) authentication.getPrincipal());
        var createdUserId = postService.createPost(postText, user);
        return ResponseEntity.ok().body(createdUserId);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> feed(@RequestParam Integer offset,
                                              @RequestParam Integer limit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<PostDto> posts = postService.feed(((UserEntity) authentication.getPrincipal()).getId(), offset, limit);
        return ResponseEntity.ok().body(posts);
    }
}
