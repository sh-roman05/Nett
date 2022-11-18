package com.roman.nett.controller;

import com.roman.nett.dto.NewPostRequestDto;
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

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getPosts(Pageable pageable) {
        var posts = postService.getAll(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping(path = "/", params = { "user" })
    public ResponseEntity<?> getPostsByUser(@RequestParam("user") Long id, Pageable pageable) {
        var posts = postService.getAllByUserId(id, pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}/")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        log.info("IN getPost - trying to get post with id {}", postId);
        return postService.getPostById(postId)
                .map(postPro -> {
                    log.info("IN getPost - post with id {} found", postId);
                    return ResponseEntity.ok(postPro);
                }).orElseThrow(() -> {
                    log.warn("IN getPost - attempt to get post with id {} failed with exception", postId);
                    return new ResourceNotFoundException("Post with id " + postId + " not found");
                });
    }

    /////
    @PostMapping("/")
    public ResponseEntity<?> newPost(@AuthenticationPrincipal JwtUser jwtUser,
                                     @Valid @RequestBody NewPostRequestDto newPostRequestDto) {
        //var user = userService.findById(jwtUser.getId());
        var user = User.builder().id(jwtUser.getId()).build();
        postService.addNewPost(newPostRequestDto, user);
        return ResponseEntity.ok().build();
    }

    /////
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal JwtUser jwtUser,
                                        @PathVariable Long id) {

        //Ошибка если мне нельзя удалять
        //Ошибка если не найден пост
        //Делать разные?

        //post id 15 можно удалить для теста

        var post = postService.getPostById(id);

        post.ifPresentOrElse(item -> {
            log.info(item.getText());
        }, () -> {
            throw new ResourceNotFoundException("Ошибка пост не найден");
        });



        /*post.map(item -> {


            return item;


        }).orElseThrow(() -> new ResourceNotFoundException("Ошибка пост не найден"));*/


        /*if(post.isPresent())
        {
            if(post.get().getUserId() == jwtUser.getId())
        }
        else
        {
            //Ошибка пост не найден
            throw new ResourceNotFoundException("Ошибка пост не найден");
        }*/







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
















