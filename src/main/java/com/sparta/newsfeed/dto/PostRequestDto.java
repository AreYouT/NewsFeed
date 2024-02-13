package com.sparta.newsfeed.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDto {

    @NotBlank
    private String category;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;
}
