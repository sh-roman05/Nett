package com.roman.nett.controller;

import com.roman.nett.service.PostService;
import com.roman.nett.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getPosts() {
        //
        return ResponseEntity.ok(postService.getAll());
    }


    /*
    *
    * {
    "timestamp": "2022-11-11T21:09:31.848+00:00",
    "status": 404,
    "error": "Not Found",
    "path": "/api/v1/posts"
}
    * */

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {


        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> newPost() {


        return null;
    }

}
