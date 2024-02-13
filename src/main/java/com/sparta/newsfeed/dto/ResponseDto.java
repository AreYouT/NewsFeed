package com.sparta.newsfeed.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseDto<T> {
    private int httpStatus;
    private String message;
    private T data;
}
