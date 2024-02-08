package com.sparta.newsfeed.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private HttpStatus httpStatus;
    private String message;
    private T data;
}
