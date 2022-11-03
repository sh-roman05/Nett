package com.roman.nett.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequestDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
