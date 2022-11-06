package com.roman.nett.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidErrorDto {
    private String field;
    private String error;
}
