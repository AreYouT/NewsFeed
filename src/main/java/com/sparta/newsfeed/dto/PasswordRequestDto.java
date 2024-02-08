package com.sparta.newsfeed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordRequestDto {
    @NotBlank
    private String checkPassword;
    @NotBlank
    private String newPassword;
}
