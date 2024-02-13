package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.*;
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

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<String>> register(
            @Valid @RequestBody RegisterRequestDto requestDto,
            BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.ok()
                    .body(ResponseDto.<String>builder()
                            .httpStatus(HttpStatus.BAD_REQUEST.value())
                            .message("회원가입에 실패했습니다.")
                            .build());
        }

        userService.register(requestDto);
        return ResponseEntity.ok()
                .body(ResponseDto.<String>builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("회원가입에 성공하였습니다.")
                        .build());
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> userUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UserInfoRequestDto requestDto){

        log.info("회원정보 수정");

        UserInfoResponseDto userInfoResponseDto = userService.userUpdate(userDetails, requestDto);

        return ResponseEntity.ok()
                .body(ResponseDto.<UserInfoResponseDto>builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("회원정보 수정 성공")
                        .data(userInfoResponseDto)
                        .build());
    }

    @PatchMapping("/update/password")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> passwordUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody PasswordRequestDto requestDto){

        log.info("비밀번호 변경");

        UserInfoResponseDto userInfoResponseDto = userService.passwordUpdate(userDetails, requestDto);

        return ResponseEntity.ok()
                .body(ResponseDto.<UserInfoResponseDto>builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("회원정보 수정 성공")
                        .data(userInfoResponseDto)
                        .build());
    }
}
