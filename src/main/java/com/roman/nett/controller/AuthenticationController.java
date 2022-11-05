package com.roman.nett.controller;

import com.roman.nett.dto.AuthRequestDto;
import com.roman.nett.dto.RegisterRequestDto;
import com.roman.nett.dto.TokenResponseDto;
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
            log.info(requestDto.toString());

            String username = requestDto.getUsername();
            String password = requestDto.getPassword();
            //todo нужно после проверки делать?
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = userService.findByUsername(username);

            log.info("User is null? " + (user == null));

            if(user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(user);


//            Map<Object, Object> response = new HashMap<>();
//            response.put("username", username);
//            response.put("token", token);

            var response = TokenResponseDto.builder()
                    .username(username)
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

        //todo Проверка значений на уникальность

        var check = userService.findByUsername(registerRequestDto.getUsername());
        if(check != null) return null;


        //Создание и регистрация пользователя
        var user = User.builder()
                .username(registerRequestDto.getUsername())
                .email(registerRequestDto.getEmail())
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .password(registerRequestDto.getPassword())
                .build();

        var registeredUser = userService.register(user);

        String token = jwtTokenProvider.createToken(registeredUser);

        log.info("User '{}' is registered", registeredUser.getUsername());

        var response = TokenResponseDto.builder()
                .username(registeredUser.getUsername())
                .token(token)
                .build();

        return ResponseEntity.status(201).body(response);
    }





}
