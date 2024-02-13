package com.sparta.newsfeed.dto.request;


import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordRequestDto {

    @Pattern(regexp="^[a-zA-Z0-9]{8,15}$") // 비밀번호 대소문자 및 숫자 8~15 문자
    private String checkPassword;
    @Pattern(regexp="^[a-zA-Z0-9]{8,15}$") // 비밀번호 대소문자 및 숫자 8~15 문자
    private String newPassword;
}
