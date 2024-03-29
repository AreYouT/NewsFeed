package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.request.PasswordRequestDto;
import com.sparta.newsfeed.dto.request.RegisterRequestDto;
import com.sparta.newsfeed.dto.request.UserUpdateRequestDto;
import com.sparta.newsfeed.dto.response.ResponseDto;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(
            @Valid @RequestBody RegisterRequestDto requestDto,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return handleValidationResult(bindingResult);
        }

        userService.register(requestDto);
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("회원가입에 성공하였습니다.")
                        .build());
    }

    @GetMapping("{user_id}")
    public ResponseEntity<ResponseDto> getUserInfo(@PathVariable Long user_id) {
        ResponseDto responseDto = userService.getUserInfo(user_id);
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseDto> userUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UserUpdateRequestDto requestDto,
            BindingResult bindingResult){

        log.info("회원정보 수정");

        if(bindingResult.hasErrors()){
            return handleValidationResult(bindingResult);
        }

        userService.userUpdate(userDetails.getUser(), requestDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("회원정보 수정 성공")
                        .build());
    }

    @PatchMapping("/update/password")
    public ResponseEntity<ResponseDto> passwordUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody PasswordRequestDto requestDto,
            BindingResult bindingResult){

        log.info("비밀번호 변경");

        if(bindingResult.hasErrors()){
            return handleValidationResult(bindingResult);
        }

        userService.passwordUpdate(userDetails, requestDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("회원정보 수정 성공")
                        .build());
    }

    private ResponseEntity<ResponseDto> handleValidationResult(BindingResult bindingResult) {
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            return ResponseEntity.badRequest()
                    .body(ResponseDto.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST.value())
                            .message(fieldError.getDefaultMessage())
                            .build());
        }
        throw new RuntimeException("예기치 못한 오류가 발생했습니다.");
    }
}
