package com.roman.nett.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record NewPostRequestDto (
        @Length(max = 240, message = "Text cannot be longer than 240 characters")
        @NotBlank(message = "Text cannot be empty")
        String text
) { }
