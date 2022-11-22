package com.roman.nett.controller;

import com.roman.nett.dto.UserDto;
import com.roman.nett.dto.UserResponseDto;
import com.roman.nett.exception.NoEntityException;
import com.roman.nett.exception.ResourceNotFoundException;
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


    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return userService.getById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@AuthenticationPrincipal JwtUser jwtUser,
                                      @PathVariable Long id,
                                      @Valid @RequestBody UserDto userDto) {

        //Обновляемый пользователь должен совпадать с авторизованным
        boolean isEquals = id.equals(jwtUser.getId());

        if(isEquals) {
            //Изменяем пользователя
            //userService.editUser(jwtUser, userDto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).body("Вы можете обновить только свой профиль");
        }

    }



    /*@PutMapping("/{username}")
    public ResponseEntity<?> editUser(@AuthenticationPrincipal JwtUser jwtUser,
                                      @PathVariable String username,
                                      @Valid @RequestBody UserDto userDto) {

        //Обновляемый пользователь должен совпадать с авторизованным
        boolean isEquals = username.equalsIgnoreCase(jwtUser.getUsername());

        if(isEquals) {
            //Изменяем пользователя
            userService.editUser(jwtUser, userDto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }

    }*/





}
