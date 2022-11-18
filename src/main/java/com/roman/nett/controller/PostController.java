package com.roman.nett.controller;

import com.roman.nett.dto.NewPostRequestDto;
import com.roman.nett.dto.PostResponseDto;
import com.roman.nett.exception.NoEntityException;
import com.roman.nett.model.entity.Post;
import com.roman.nett.model.entity.User;
import com.roman.nett.security.jwt.JwtUser;
import com.roman.nett.service.PostService;
import com.roman.nett.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getPosts(Pageable pageable) {
        log.info("getPosts + pageable");
        var posts = postService.getAll(pageable);
        var result = posts.stream()
                .map(item -> PostResponseDto.builder()
                        .id(item.getId())
                        .text(item.getText())
                        .created(item.getCreated().getTime())
                        .build()
                ).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/", params = { "user" })
    public ResponseEntity<?> getPostsByUser(@RequestParam("user") Long id, Pageable pageable) {
        log.info("getPostsByUser + pageable");
        var user = User.builder().id(id).build();
        var posts = postService.getAllByUser(user);
        var result = posts.stream()
                .map(item -> PostResponseDto.builder()
                        .id(item.getId())
                        .text(item.getText())
                        .created(item.getCreated().getTime())
                        .build()
                ).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{postId}/")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        return postService.getPostById(postId).map(
                item -> PostResponseDto.builder()
                        .id(item.getId())
                        .text(item.getText())
                        .created(item.getCreated().getTime())
                        .build()
        ).map(ResponseEntity::ok).orElseThrow(() -> new NoEntityException(postId));
    }

    @PostMapping("/")
    public ResponseEntity<?> newPost(@AuthenticationPrincipal JwtUser jwtUser,
                                     @Valid @RequestBody NewPostRequestDto newPostRequestDto) {
        //var user = userService.findById(jwtUser.getId());
        var user = User.builder().id(jwtUser.getId()).build();
        var newPost = Post.builder()
                .text(newPostRequestDto.text())
                .user(user)
                .created(new Date())
                .build();
        postService.addNewPost(newPost);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal JwtUser jwtUser,
                                        @PathVariable Long id) {

        //Ошибка если мне нельзя удалять
        //Ошибка если не найден пост
        //Делать разные?

        //post id 15 можно удалить для теста

        var user = userService.findById(jwtUser.getId());

        var result = postService.deletePost(user, id);

        if(result)
        {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).body("пост не найден или вы не его автор");
        }
    }



}
















