package com.sparta.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@Builder
public class ResponseDto<T> {
    private int httpStatus;
    private String message;
    private T data;
}
