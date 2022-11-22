package com.roman.nett.service;

import com.roman.nett.dto.NewPostRequestDto;
import com.roman.nett.dto.PostResponseDto;
import com.roman.nett.dto.projection.PostView;
import com.roman.nett.model.entity.Post;
import com.roman.nett.model.entity.User;
import com.roman.nett.security.jwt.JwtUser;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    void deletePost(Long postId);
    List<Post> getAll();
    boolean deletePost(User user, Long postId);
    Optional<PostView> getPostById(Long postId);
    List<PostView> getAllByUserId(Long userId, Pageable pageable);



    //Получить все посты
    List<PostView> getAll(Pageable pageable);
    //Добавить новый пост и получить добавленный
    PostResponseDto addNewPost(NewPostRequestDto newPostRequestDto, JwtUser jwtUser);
}
