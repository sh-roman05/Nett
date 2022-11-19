package com.roman.nett.service;

import com.roman.nett.dto.NewPostRequestDto;
import com.roman.nett.dto.projection.PostPro;
import com.roman.nett.model.entity.Post;
import com.roman.nett.model.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    void deletePost(Long postId);
    List<Post> getAll();
    boolean deletePost(User user, Long postId);
    Optional<PostPro> getPostById(Long postId);
    List<PostPro> getAllByUserId(Long userId, Pageable pageable);



    //Получить все посты
    List<PostPro> getAll(Pageable pageable);
    //Добавить новый пост и получить добавленный
    PostPro addNewPost(NewPostRequestDto newPostRequestDto, User user);
}
