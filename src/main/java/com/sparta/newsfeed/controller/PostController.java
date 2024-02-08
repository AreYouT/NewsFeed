package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.controller.ResponseForm;
import com.sparta.newsfeed.controller.StatusEnum;
import com.sparta.newsfeed.dto.PostListResponseDto;
import com.sparta.newsfeed.dto.PostRequestDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.response.PostResponse;
import com.sparta.newsfeed.security.UserDetailsImpl;
import com.sparta.newsfeed.service.PostService;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{category}")
    public ResponseEntity<ResponseForm> findByCategoryNameToList(
            @PathVariable String category
    ){
        List<PostListResponseDto> responseDtoList = postService.findByCategoryNameToList(category);

        ResponseForm form = new ResponseForm();

        form.setStatus(StatusEnum.OK);
        form.setData(responseDtoList);

        return new ResponseEntity<>(form,HttpStatus.OK);
    }

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
