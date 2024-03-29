package com.sparta.newsfeed.dto.response;

import com.sparta.newsfeed.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDto {
    private String username;
    private String email;
    private String mbti;
    private String profileDescription;

    public UserInfoResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.mbti = user.getMbti();
        this.profileDescription = user.getProfileDescription();
    }
}
