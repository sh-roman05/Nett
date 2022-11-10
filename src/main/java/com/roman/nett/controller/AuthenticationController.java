package com.roman.nett.controller;

import com.roman.nett.dto.AuthRequestDto;
import com.roman.nett.dto.RegisterRequestDto;
import com.roman.nett.dto.TokenResponseDto;
import com.roman.nett.exception.RegisterUserErrorException;
import com.roman.nett.security.jwt.JwtTokenProvider;
import com.roman.nett.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;

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
            var username = requestDto.username();
            var password = requestDto.password();
            //Проверяет в базе логин и пароль
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = userService.findByUsername(username);

            if(user == null)
                throw new UsernameNotFoundException("User with username: " + username + " not found");

            String token = jwtTokenProvider.createToken(user);

            log.info("IN login - for user: {} successfully generated new jwt token", user.getUsername());

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

        var existsUsername = userService.existsUsername(registerRequestDto.username());
        var existsEmail = userService.existsEmail(registerRequestDto.email());

        if(!existsUsername && !existsEmail) {

            //Зарегистрировать
            var registeredUser = userService.register(registerRequestDto);

            String token = jwtTokenProvider.createToken(registeredUser);

            var response = TokenResponseDto.builder()
                    .username(registeredUser.getUsername())
                    .token(token)
                    .build();

            log.info("IN login - for user: {} successfully generated new jwt token", registeredUser.getUsername());

            return ResponseEntity.status(201).body(response);

        } else {
            var errors = new LinkedHashMap<>();
            if(existsUsername) errors.put("username", "username already exists");
            if(existsEmail) errors.put("email", "email already exists");
            throw new RegisterUserErrorException(errors);
        }
    }





}
