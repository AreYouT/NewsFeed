package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.PostRequestDto;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class PostController {

    private final PostService postService;


    @PostMapping("/save")
    public ResponseEntity<ResponseForm> savePost(
            @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

        postService.savePost(requestDto, userDetails.getUser());

        ResponseForm form = new ResponseForm();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        form.setStatus(StatusEnum.CREATED);
        form.setMessage("게시글이 정상적으로 등록되었습니다.");

        return new ResponseEntity<>(form, headers, HttpStatus.CREATED);

    }







}
