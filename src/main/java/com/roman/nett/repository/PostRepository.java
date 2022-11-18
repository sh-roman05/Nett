package com.roman.nett.repository;

import com.roman.nett.model.entity.Post;
import com.roman.nett.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying @Query("delete from Post p where p.id = :postId and p.user = :user")
    int deleteByIdAndUser(@Param("postId") Long postId, @Param("user") User user);





    List<Post> findAllByUser(User user);
    List<Post> getAllByUserOrderByCreatedDesc(User user);
    List<Post> findAllByOrderByCreatedDesc();
    List<Post> findAllByOrderByCreatedDesc(Pageable pageable);

}
