package com.roman.nett.controller;

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

    @Autowired
    public PostController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getPosts() {
        //
        return null;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {


        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> newPost() {


        return null;
    }

}
