package com.roman.nett.repository;

import com.roman.nett.dto.UserDto;
import com.roman.nett.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    //А какие именно поля мне нужно обновить? Все которые прислал? А те что null?
    @Query(value = """
            update User u set
            u.email = :#{#userDto.email},
            u.firstName = :#{#userDto.firstName},
            u.lastName = :#{#userDto.lastName}
            where u.id = :#{#id}
            """)
    User updateUser(@Param("userDto") UserDto userDto, @Param("id") Long id);
}
