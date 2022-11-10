package com.roman.nett.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public record AuthRequestDto (
        @Length(min = 3, max = 20, message = "Username must be between 3-20 characters")
        @NotBlank(message = "Username cannot be blank")
        String username,
        @NotBlank(message = "Password cannot be blank")
        String password
) { }
