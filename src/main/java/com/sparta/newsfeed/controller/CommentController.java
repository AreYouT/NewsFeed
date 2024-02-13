package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.request.CommentRequestDto;
import com.sparta.newsfeed.dto.response.CommentResponseDto;
import com.sparta.newsfeed.dto.response.ResponseDto;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{post_id}/comment/create")
    public ResponseEntity<ResponseDto> createComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CommentRequestDto requestDto,
            @PathVariable Long post_id
    ) {

        return ResponseEntity.ok()
                .body(ResponseDto.<CommentResponseDto>builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("댓글 작성 성공")
                        .build());
    }

    @PatchMapping("/{post_id}/comment/{comment_id}/update")
    public ResponseEntity<ResponseDto> updateComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CommentRequestDto requestDto,
            @PathVariable Long post_id,
            @PathVariable Long comment_id
    ) {

        return ResponseEntity.ok()
                .body(ResponseDto.<CommentResponseDto>builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("댓글 수정 성공")
                        .build());
    }

    @DeleteMapping("/{post_id}/comment/{comment_id}/delete")
    public ResponseEntity<ResponseDto> deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long post_id,
            @PathVariable Long comment_id
    ) {

        commentService.deleteComment(userDetails.getUser(), post_id, comment_id);

        return ResponseEntity.ok()
                .body(ResponseDto.<Void>builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("댓글 삭제 성공")
                        .build());
    }
}
