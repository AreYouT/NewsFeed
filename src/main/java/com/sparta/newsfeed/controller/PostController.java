package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.PostRequestDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.dto.ResponseDto;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class PostController {

    private final PostService postService;


    @PostMapping("/save")
    public ResponseEntity<ResponseDto<PostResponseDto>> savePost(
            @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
//        빌더 패턴 적용전 코드
//        PostResponseDto responseDto = postService.savePost(requestDto, userDetails.getUser());
//
//        ResponseDto<PostResponseDto> form = new ResponseDto<>(HttpStatus.OK, "success", responseDto);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
//
//        return new ResponseEntity<>(form, headers, HttpStatus.CREATED);
        return ResponseEntity.ok()
                .body(ResponseDto.<PostResponseDto>builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("success")
                        .data(postService.savePost(requestDto, userDetails.getUser()))
                        .build());

    }

    @GetMapping("/{category}")
    public ResponseEntity<ResponseDto<List<PostResponseDto>>> findByCategoryNameToList(
           @PathVariable String category
    ){
        return ResponseEntity.ok()
                .body(ResponseDto.<List<PostResponseDto>>builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("success")
                        .data(postService.findByCategoryNameToList(category))
                        .build());
    }
}
