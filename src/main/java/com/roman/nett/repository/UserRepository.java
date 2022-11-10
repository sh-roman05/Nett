package com.roman.nett.repository;

import com.roman.nett.dto.UserDto;
import com.roman.nett.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);

    @Modifying
    @Transactional
    @Query(value = """
            update User u set
            u.email = :#{#userDto.email},
            u.firstName = :#{#userDto.firstName},
            u.lastName = :#{#userDto.lastName},
            u.aboutMe = :#{#userDto.aboutMe}
            where upper(u.username) = upper(:#{#username})
            """)
    void updateUser(@Param("userDto") UserDto userDto, @Param("username") String username);
}
