package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.PasswordRequestDto;
import com.sparta.newsfeed.dto.RegisterRequestDto;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequestDto requestDto,
            BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>("회원가입에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }

        userService.register(requestDto);
        return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.OK);
    }



    @PatchMapping("/update")
    public ResponseEntity<UserInfoResponseDto> userUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UserInfoRequestDto requestDto){

        log.info("회원정보 수정");

        UserInfoResponseDto userInfoResponseDto = userService.userUpdate(userDetails, requestDto);

        log.info(userInfoResponseDto.getUsername());
        return new ResponseEntity<>(userInfoResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/update/password")
    public ResponseEntity<UserInfoResponseDto> passwordUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody PasswordRequestDto requestDto){

        log.info("비밀번호 변경");

        UserInfoResponseDto userInfoResponseDto = userService.passwordUpdate(userDetails, requestDto);

        return new ResponseEntity<>(userInfoResponseDto, HttpStatus.OK);
    }
}
