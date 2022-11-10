package com.roman.nett.dto;

import lombok.Builder;

@Builder
public record ValidErrorDto (
        String field,
        String error
) { }
