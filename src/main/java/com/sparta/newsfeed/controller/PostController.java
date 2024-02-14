package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.request.PostRequestDto;
import com.sparta.newsfeed.dto.request.UpdateRequestDto;
import com.sparta.newsfeed.dto.response.PostResponseDto;
import com.sparta.newsfeed.dto.response.ResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class PostController {

    private final PostService postService;


    @PostMapping("/save")
    public ResponseEntity<ResponseDto> savePost(
            @RequestBody PostRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        postService.savePost(requestDto, userDetails.getUser());

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("게시글이 정상적으로 등록되었습니다.")
                        .build());

    }

    @GetMapping("/mbti/{category}")
    public ResponseEntity<ResponseDto> findByCategoryNameToList(
            @PathVariable String category
    ){
        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("success")
                        .data(postService.findByCategoryNameToList(category))
                        .build());
    }

    @GetMapping("/home")
    public ResponseEntity<ResponseDto> recommendedPosts() {
        List<PostResponseDto> postResponseDtos = postService.getRecommendedPostResponseDtos();
        return ResponseEntity.ok().body(
                ResponseDto.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("추천 순으로 전체 게시글이 조회되었습니다.")
                        .data(postResponseDtos)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getPostById(
            @PathVariable Long id
    ) {
        Post post = postService.getPostById(id);
        postService.updateView(id);
        return createPostResponse(post);
    }

    private ResponseEntity<ResponseDto> createPostResponse(Post post) {
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return ResponseEntity.ok().body(ResponseDto.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("선택한 게시글이 조회되었습니다.")
                .data(postResponseDto)
                .build());
    }


    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdateRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Post post = postService.updatePost(postId, dto,userDetails.getUser());

        return ResponseEntity.ok().body(
                ResponseDto.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("선택한 게시글이 수정되었습니다.")
                        .build()
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto> deletePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        postService.deletePost(postId, userDetails.getUser());

        return ResponseEntity.ok().body(
                ResponseDto.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("선택한 게시글이 삭제되었습니다.")
                        .build()
        );
    }

    @PostMapping("{postId}/like")
    public ResponseEntity<ResponseDto> likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        postService.likePost(postId, userDetails.getUser());

        return ResponseEntity.ok().body(
                ResponseDto.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("게시글에 좋아요를 했습니다.")
                        .build()
        );
    }
}
