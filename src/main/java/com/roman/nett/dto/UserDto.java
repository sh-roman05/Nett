package com.roman.nett.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Builder
public record UserDto (
        @Email(message = "Incorrect email")
        String email,
        String firstName,
        String lastName,
        String aboutMe
) { }

//Количество символов должно быть между 3 и 20. Первый символ не может быть цифрой.
//Специальные символы не должны повторятся. Специальные символы не могут быть первым или последним символом
    /*@Pattern(regexp = "^[a-zA-Z]([._-](?![._-])|[a-zA-Z0-9]){1,18}[a-zA-Z0-9]$",
        message = "Username must be between 3 to 20, consist of latin letters and symbols (._-)")
    private String username;*/
