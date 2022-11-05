package com.roman.nett.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDto {
    private String username;
    private String token;
}
