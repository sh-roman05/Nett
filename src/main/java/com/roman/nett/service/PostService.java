package com.roman.nett.service;

import com.roman.nett.model.entity.Post;
import com.roman.nett.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getAll();
    List<Post> getAllByUser(User user);
    Optional<Post> getPostById(Long postId);
    void addNewPost(Post post);
    boolean deletePost(User user, Long postId);
}
