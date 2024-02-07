package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.User;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long id;

    private String title;

    private String contents;

    private Long likeCount;

    private Long viewCount;

    private String category;

    private User user;



}
