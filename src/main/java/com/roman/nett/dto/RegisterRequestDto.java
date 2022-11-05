package com.roman.nett.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequestDto {

    private String username;

    //todo сделать проверку пароля
    //password has a minimum of 6 characters, at least 1 uppercase letter, 1 lowercase letter, and 1 number with no spaces.
    //@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Пароль должен быть от 8 до 20 символов, ")
    private String password;

    @Email(message = "Incorrect 'email'")
    private String email;

    @Length(min = 2, max = 20, message = "'firstName' must be between 2 and 20 characters")
    private String firstName;

    @Length(min = 2, max = 20, message = "'lastName' must be between 2 and 20 characters")
    private String lastName;
}
