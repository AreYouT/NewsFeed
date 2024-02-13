package com.sparta.newsfeed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@Builder
public class ResponseDto {
    private int httpStatus;
    private String message;
    private Object data;
}
