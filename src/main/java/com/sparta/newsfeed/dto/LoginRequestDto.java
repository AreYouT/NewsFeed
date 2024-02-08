package com.sparta.newsfeed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String username;

    private String password;
}
