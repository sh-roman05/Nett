package com.roman.nett.dto;

import lombok.Builder;

@Builder
public record TokenResponseDto (String username,
                                String token) {
}
