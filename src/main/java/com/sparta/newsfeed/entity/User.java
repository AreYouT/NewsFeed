package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.request.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Transient
    final String DEFAULTMESSAGE = "잘 부탁드립니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String mbti;

    @Column(nullable = false)
    private String profileDescription;

    public User(String username, String password, String email, String mbti) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.mbti = mbti.toUpperCase();
        this.profileDescription = DEFAULTMESSAGE;
    }

    public void userInfoUpdate(UserUpdateRequestDto requestDto) {
        this.email = requestDto.getEmail();
        this.mbti = requestDto.getMbti().toUpperCase();
        this.profileDescription = requestDto.getProfileDescription();
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
