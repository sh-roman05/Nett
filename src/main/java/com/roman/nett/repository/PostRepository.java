package com.roman.nett.repository;

import com.roman.nett.model.entity.Post;
import com.roman.nett.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    //void deletePostByIdAndUserIsContaining(Long postId, User user);
    //void deletePostByIdAndUser(Long postId, User user);
    boolean deleteByUserAndId(User user, Long postId);
    List<Post> getAllByUser(User user);
}
