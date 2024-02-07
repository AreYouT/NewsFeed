package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDto {
    private String username;
    private String email;
    private String mbti;

    public UserInfoResponseDto(User user) {
        this.username = username;
        this.email = email;
        this.mbti = mbti;
    }
}
