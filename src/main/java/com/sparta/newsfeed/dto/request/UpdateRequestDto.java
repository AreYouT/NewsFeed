package com.sparta.newsfeed.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String contents;
}
