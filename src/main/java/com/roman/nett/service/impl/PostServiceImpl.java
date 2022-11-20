package com.roman.nett.service.impl;

import com.roman.nett.dto.NewPostRequestDto;
import com.roman.nett.dto.projection.PostPro;
import com.roman.nett.exception.UnknownRepositoryException;
import com.roman.nett.model.entity.Post;
import com.roman.nett.model.entity.User;
import com.roman.nett.repository.PostRepository;
import com.roman.nett.security.jwt.JwtUser;
import com.roman.nett.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public List<PostPro> getAll(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedDesc(pageable);
    }

    //Получить всех пользователей по id
    @Override
    public List<PostPro> getAllByUserId(Long userId, Pageable pageable) {
        var user = User.builder().id(userId).build();
        return postRepository.findAllByUserOrderByCreatedDesc(user, pageable);
    }

    @Override
    public Optional<PostPro> getPostById(Long postId) {
        return postRepository.getPostById(postId);
    }

    @Override
    public PostPro addNewPost(NewPostRequestDto newPostRequestDto, JwtUser jwtUser) {
        var newPost = Post.builder()
                .text(newPostRequestDto.text())
                .user(jwtUser.convertToUser())
                .created(new Date())
                .build();
        try {
            return postRepository.savePost(newPost);
        } catch (DataAccessException ex) {
            log.error("IN addNewPost - thrown DataAccessException with message: {}", ex.getMessage());
            throw new UnknownRepositoryException("При сохранении поста в базу произошла ошибка");
        }
    }

    @Override
    public boolean deletePost(User user, Long postId) {
        var result = postRepository.deleteByIdAndUser(postId, user);
        return result > 0;
    }
}
