package com.sparta.newsfeed.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {

    private String category;

    private String title;

    private String contents;
}
