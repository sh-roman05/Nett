package com.roman.nett.dto;

import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Builder
public record RegisterRequestDto (
        String username,
        String password,
        @Email(message = "Incorrect email")
        String email,
        @Length(min = 2, max = 20, message = "firstName must be between 2 and 20 characters")
        String firstName,
        @Length(min = 2, max = 20, message = "lastName must be between 2 and 20 characters")
        String lastName
) { }


//todo сделать проверку пароля
//password has a minimum of 6 characters, at least 1 uppercase letter, 1 lowercase letter, and 1 number with no spaces.
//@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Пароль должен быть от 8 до 20 символов, ")
