package com.roman.nett.controller;

import com.roman.nett.dto.NewPostRequestDto;
import com.roman.nett.exception.NoPermissionException;
import com.roman.nett.exception.ResourceNotFoundException;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getPosts(Pageable pageable) {
        log.info("IN getPostsByUser - trying to get {} posts on page {}",
                pageable.getPageSize(), pageable.getPageNumber());
        var posts = postService.getAll(pageable);
        if (posts.size() == 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(posts);
    }

    @GetMapping(path = "/", params = { "user" })
    public ResponseEntity<?> getPostsByUser(@RequestParam("user") Long id, Pageable pageable) {
        log.info("IN getPostsByUser - trying to get {} posts on page {} owned by user with id {}",
                pageable.getPageSize(), pageable.getPageNumber(), id);
        var posts = postService.getAllByUserId(id, pageable);
        if (posts.size() == 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        log.info("IN getPost - trying to get post with id {}", id);
        return postService.getPostById(id)
                .map(postPro -> {
                    log.info("IN getPost - post with id {} found", id);
                    return ResponseEntity.ok(postPro);
                }).orElseThrow(() -> {
                    log.warn("IN getPost - attempt to get post with id {} failed with exception", id);
                    return new ResourceNotFoundException("Post with id " + id + " not found");
                });
    }

    @PostMapping("/")
    public ResponseEntity<?> newPost(@AuthenticationPrincipal JwtUser jwtUser,
                                     @Valid @RequestBody NewPostRequestDto newPostRequestDto) {
        log.info("IN newPost - trying to create a post");
        var post = postService.addNewPost(newPostRequestDto, jwtUser);
        log.info("IN newPost - post was successfully created with id {}", post.getId());
        return ResponseEntity.ok().body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal JwtUser jwtUser, @PathVariable Long id) {
        return postService.getPostById(id)
                .map(item -> {
                    //Проверяем, принадлежит ли пост пользователю
                    if (jwtUser.getId().equals(item.getUserId())) {
                        postService.deletePost(item.getId());
                        log.info("IN deletePost - Post with id {} successfully deleted", id);
                        return ResponseEntity.ok().build();
                    } else {
                        log.warn("IN deletePost - Attempt to delete post with id {} rejected and thrown NoPermissionException", id);
                        throw new NoPermissionException("The post with id {} does not belong to you. You cannot remove it");
                    }
                }).orElseThrow(() -> {
                    log.warn("IN deletePost - Post with id {} not found and thrown ResourceNotFoundException", id);
                    throw new ResourceNotFoundException("Post with id " + id + " not found");
                });
    }



}
















