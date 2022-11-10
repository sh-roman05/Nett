package com.roman.nett.dto;

import lombok.Builder;

@Builder
public record UserResponseDto (
        String username,
        String email,
        String firstName,
        String lastName,
        String aboutMe,
        Long created
) { }
