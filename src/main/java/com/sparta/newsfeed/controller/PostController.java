package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.PostListResponseDto;
import com.sparta.newsfeed.dto.PostRequestDto;
import com.sparta.newsfeed.entity.Post;
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

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<ResponseForm> updatePost(@PathVariable Long postId,
                                                   @RequestBody PostRequestDto dto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Post post = postService.updatePost(postId, dto,userDetails.getUser());

        ResponseForm form = new ResponseForm();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        form.setStatus(StatusEnum.OK);
        form.setMessage("게시글이 정상적으로 수정되었습니다.");
        form.setData(post);

        return new ResponseEntity<>(form, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseForm> deletePost(@PathVariable Long postId,
                                                   @RequestBody PostRequestDto dto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deleteToDo(postId, userDetails.getUser());

        ResponseForm form = new ResponseForm();

        form.setStatus(StatusEnum.OK);
        form.setMessage("게시글이 정상적으로 삭제되었습니다.");

        return new ResponseEntity<>(form, HttpStatus.OK);
    }





}
