package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.UserInfoRequestDto;
import com.sparta.newsfeed.dto.UserInfoResponseDto;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PatchMapping("/update")
    public ResponseEntity<UserInfoResponseDto> userUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UserInfoRequestDto requestDto){
        log.info("회원정보 수정");

        UserInfoResponseDto userInfoResponseDto = userService.userUpdate(userDetails.getUser(), requestDto);

        return new ResponseEntity<UserInfoResponseDto>(userInfoResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/update/password")
    public ResponseEntity<UserInfoResponseDto> passwordUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody String password){
        log.info("비밀번호 변경");

        UserInfoResponseDto userInfoResponseDto = userService.passwordUpdate(userDetails.getUser(), password);

        return new ResponseEntity<UserInfoResponseDto>(userInfoResponseDto, HttpStatus.OK);
    }
}
