package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.response.PostResponse;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards")
public class PostController {

    private PostService postService;


    @GetMapping("/home")
    public ResponseEntity<PostResponse<List<PostResponseDto>>> getRecommendedPosts() {
        List<Post> posts = postService.getRecommendedPosts();
        List<PostResponseDto> responseDtos = posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(PostResponse.<List<PostResponseDto>>builder()
                .StatusCode(HttpStatus.OK.value())
                .message("전체 게시글을 추천 순으로 조회합니다.")
                .date(responseDtos)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse<PostResponseDto>> getPostById(@PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return ResponseEntity.ok().body(PostResponse.<PostResponseDto>builder()
                    .StatusCode(HttpStatus.OK.value())
                    .message("선택한 게시글을 조회합니다.")
                    .date(postResponseDto)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
}
