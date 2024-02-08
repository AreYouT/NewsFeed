package com.sparta.newsfeed.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse<T> {

    private Integer StatusCode;
    private String message;
    private T date;
}
