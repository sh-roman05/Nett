package com.roman.nett.repository;

import com.roman.nett.dto.projection.PostPro;
import com.roman.nett.dto.projection.UserPro;
import com.roman.nett.model.entity.Post;
import com.roman.nett.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    //Удалить пост, если его автор user
    @Modifying @Query("delete from Post p where p.id = :postId and p.user = :user")
    int deleteByIdAndUser(@Param("postId") Long postId, @Param("user") User user);

    //Получить пост по его id
    @Query("select p from Post p where p.id = :id")
    Optional<PostPro> getPostById(long id);

    //Получить посты у пользователя отсортированные по новизне
    List<PostPro> findAllByUserOrderByCreatedDesc(User user, Pageable pageable);

    //Получить все посты отсортированные по новизне
    List<PostPro> findAllByOrderByCreatedDesc(Pageable pageable);
}
