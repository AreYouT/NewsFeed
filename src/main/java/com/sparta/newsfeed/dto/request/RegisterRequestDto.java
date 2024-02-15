package com.sparta.newsfeed.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterRequestDto {
    @Pattern(regexp="^[a-z0-9]{4,10}$", message = "username은 숫자 및 알파벳 소문자 4~10자로 입력해주세요.") // 소문자 및 숫자로 4~10 문자
    private String username;
    @Pattern(regexp="^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 숫자 및 알파벳 대소문자 8~15자로 입력해주세요.") // 비밀번호 대소문자 및 숫자 8~15 문자
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "옳바른 email 형식이 아닙니다.")
    private String email;
    @Pattern(regexp="^[IEie][NSns][FTft][PJpj]$", message = "옳바른 MBTI 형식이 아닙니다.")
    private String mbti;
}
