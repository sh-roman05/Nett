package com.roman.nett.service.impl;

import com.roman.nett.dto.RegisterRequestDto;
import com.roman.nett.dto.UserDto;
import com.roman.nett.dto.projection.UserView;
import com.roman.nett.model.Status;
import com.roman.nett.model.entity.User;
import com.roman.nett.repository.RoleRepository;
import com.roman.nett.repository.UserRepository;
import com.roman.nett.security.jwt.JwtUser;
import com.roman.nett.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserView> getAll() {
        List<UserView> result = userRepository.findAllUsers();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public Optional<UserView> getById(long id) {
        return userRepository.getById(id);
    }








    @Override
    public User register(RegisterRequestDto registerRequestDto) {
        var roleUser = roleRepository.findByName("ROLE_USER");
        var roles = List.of(roleUser);
        var user = User.builder()
                .username(registerRequestDto.username())
                .email(registerRequestDto.email())
                .firstName(registerRequestDto.firstName())
                .lastName(registerRequestDto.lastName())
                .password(passwordEncoder.encode(registerRequestDto.password()))
                .roles(roles)
                .status(Status.ACTIVE)
                .build();
        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successfully registered", registeredUser.getUsername());
        return registeredUser;
    }





    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsernameIgnoreCase(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }


    @Override
    public void editUser(JwtUser jwtUser, UserDto userDto) {
        log.warn("ОЧЕНЬ ВАЖНО JwtUser: {}", jwtUser);

        userRepository.updateUser(userDto, jwtUser.getUsername());

    }



    @Override
    public boolean existsUsername(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }


}
