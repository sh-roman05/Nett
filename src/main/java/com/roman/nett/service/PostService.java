package com.roman.nett.service;

import com.roman.nett.model.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getAll();
    List<Post> getAllByUserId();
    Optional<Post> getPostById(Long postId);
}
