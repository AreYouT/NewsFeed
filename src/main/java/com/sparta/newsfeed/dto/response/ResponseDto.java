package com.sparta.newsfeed.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseDto {
    private int httpStatus;
    private String message;
    private Object data;
}
