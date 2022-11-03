package com.roman.nett.controller;

import com.roman.nett.dto.AuthRequestDto;
import com.roman.nett.dto.RegisterRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {



    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthRequestDto requestDto) {

        log.info(requestDto.toString());
        //
        return null;
    }



    @PostMapping("/register")
    public ResponseEntity<Object> registerNewUser(@RequestBody RegisterRequestDto registerRequestDto) {

        log.info(registerRequestDto.toString());

        //todo валидация данных через хибер валидатор

        //todo Проверка значений на уникальность


        //return new ResponseEntity<>("Year of birth cannot be in the future", HttpStatus.BAD_REQUEST);

        return null;
    }



//    @Override
//    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {
//
//        if (emailExist(accountDto.getEmail())) {
//            throw new EmailExistsException
//                    ("There is an account with that email adress: " + accountDto.getEmail());
//        }
//        User user = new User();
//
//        user.setFirstName(accountDto.getFirstName());
//        user.setLastName(accountDto.getLastName());
//        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
//        user.setEmail(accountDto.getEmail());
//
//        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
//        return repository.save(user);
//    }


}
