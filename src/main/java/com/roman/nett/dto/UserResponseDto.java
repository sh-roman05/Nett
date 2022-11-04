package com.roman.nett.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserResponseDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String aboutMe;
    private Long created;
}
