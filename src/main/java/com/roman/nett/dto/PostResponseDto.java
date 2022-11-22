package com.roman.nett.dto;

import lombok.Builder;

@Builder
public record PostResponseDto (
        Long id,
        Long userId,
        String text,
        Long created
) {
    //private Long estimates;
    //private Boolean meEstimate;
}
