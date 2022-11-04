package com.roman.nett.controller;

import com.roman.nett.dto.AuthRequestDto;
import com.roman.nett.dto.RegisterRequestDto;
import com.roman.nett.security.jwt.JwtTokenProvider;
import com.roman.nett.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity login(@RequestBody AuthRequestDto requestDto) {

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

            String token = jwtTokenProvider.createToken(username, user.getRoles());


            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);


            return ResponseEntity.ok(response);

            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            //return new ResponseEntity<>(result, HttpStatus.OK);

        }
        catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
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
