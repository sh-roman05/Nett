package com.roman.nett.controller;

import com.roman.nett.dto.AuthRequestDto;
import com.roman.nett.dto.RegisterRequestDto;
import com.roman.nett.dto.TokenResponseDto;
import com.roman.nett.exception.UserAlreadyExistException;
import com.roman.nett.model.entity.Role;
import com.roman.nett.model.entity.User;
import com.roman.nett.security.jwt.JwtTokenProvider;
import com.roman.nett.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDto requestDto) {
        try {
            var username = requestDto.getUsername();
            var password = requestDto.getPassword();
            //Проверяет в базе логин и пароль
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = userService.findByUsername(username);

            if(user == null)
                throw new UsernameNotFoundException("User with username: " + username + " not found");

            String token = jwtTokenProvider.createToken(user);

            var response = TokenResponseDto.builder()
                    .username(user.getUsername())
                    .token(token).build();

            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        //Проверка на уникальность
        var isExists = userService.existUsername(registerRequestDto.getUsername());

        var isEmailExists = userService;

        if(isExists) {

            //Создание и регистрация пользователя
            var user = User.builder()
                    .username(registerRequestDto.getUsername())
                    .email(registerRequestDto.getEmail())
                    .firstName(registerRequestDto.getFirstName())
                    .lastName(registerRequestDto.getLastName())
                    .password(registerRequestDto.getPassword())
                    .build();

            //Зарегистрировать
            var registeredUser = userService.register(user);

            String token = jwtTokenProvider.createToken(registeredUser);

            log.info("User '{}' is registered", registeredUser.getUsername());

            var response = TokenResponseDto.builder()
                    .username(registeredUser.getUsername())
                    .token(token)
                    .build();

            return ResponseEntity.status(201).body(response);

        } else {
            throw new UserAlreadyExistException("hjhj");
        }


    }





}
