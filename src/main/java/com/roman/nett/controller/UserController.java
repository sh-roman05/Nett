package com.roman.nett.controller;

import com.roman.nett.dto.UserDto;
import com.roman.nett.dto.UserResponseDto;
import com.roman.nett.security.jwt.JwtUser;
import com.roman.nett.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getUsers() {


        var users = userService.getAll();
        var userDtoList = users.stream().map(item ->
                UserResponseDto.builder()
                        .username(item.getUsername())
                        .email(item.getEmail())
                        .firstName(item.getFirstName())
                        .lastName(item.getLastName())
                        .aboutMe(item.getAboutMe())
                        .created(item.getCreated().getTime())
                        .build()
        ).toList();

        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {

        var user = userService.findById(id);

        if (user == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);



        var userDto = UserResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .aboutMe(user.getAboutMe())
                .created(user.getCreated().getTime())
                .build();


        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> editUser(@AuthenticationPrincipal JwtUser jwtUser,
                                      @PathVariable String username,
                                      @Valid @RequestBody UserDto userDto) {

        //Обновляемый пользователь должен совпадать с авторизованным
        boolean isEquals = username.equalsIgnoreCase(jwtUser.getUsername());

        log.info(userDto.toString());




        if(isEquals) {
            //
            //Изменяем пользователя
            userService.editUser(jwtUser, userDto);



        } else {
            return ResponseEntity.status(403).build();
        }













        return null;
    }












}
