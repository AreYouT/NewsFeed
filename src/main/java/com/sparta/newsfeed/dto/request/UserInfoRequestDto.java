package com.sparta.newsfeed.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserInfoRequestDto {
    @Pattern(regexp="^[a-z0-9]{4,10}$") // 소문자 및 숫자로 4~10 문자
    private String username;
    @Pattern(regexp="^[a-zA-Z0-9]{8,15}$") // 비밀번호 대소문자 및 숫자 8~15 문자
    private String password;
    @Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @Pattern(regexp="^[IE][NS][FT][PJ]$")
    private String mbti;
}
