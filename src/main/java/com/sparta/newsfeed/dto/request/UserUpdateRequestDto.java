package com.sparta.newsfeed.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "옳바른 email 형식이 아닙니다.")
    private String email;
    @Pattern(regexp="^[IEie][NSns][FTft][PJpj]$", message = "옳바른 MBTI 형식이 아닙니다.")
    private String mbti;
    @Size(max = 256, message = "메세지는 최대 256자까지 남기실 수 있습니다.")
    private String profileDescription;
}