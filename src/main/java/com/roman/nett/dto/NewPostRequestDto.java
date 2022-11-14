package com.roman.nett.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record NewPostRequestDto (
        @NotBlank(message = "Text cannot be blank")
        String text
) { }
