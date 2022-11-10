package com.roman.nett.service;


import com.roman.nett.dto.RegisterRequestDto;
import com.roman.nett.dto.UserDto;
import com.roman.nett.model.entity.User;
import com.roman.nett.security.jwt.JwtUser;

import java.util.List;

public interface UserService {
    User register(RegisterRequestDto registerRequestDto);
    boolean existsUsername(String username);
    boolean existsEmail(String email);

    List<User> getAll();
    User findByUsername(String username);
    User findById(Long id);
    void editUser(JwtUser jwtUser, UserDto userDto);

}
