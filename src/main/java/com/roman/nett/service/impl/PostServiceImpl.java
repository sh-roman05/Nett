package com.roman.nett.service.impl;

import com.roman.nett.model.entity.Post;
import com.roman.nett.model.entity.User;
import com.roman.nett.repository.PostRepository;
import com.roman.nett.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getAllByUser(User user) {
        return postRepository.getAllByUser(user);
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public void addNewPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public boolean deletePost(User user, Long postId) {
        return postRepository.deleteByUserAndId(user, postId);
    }
}
