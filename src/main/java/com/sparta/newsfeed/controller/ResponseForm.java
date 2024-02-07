package com.sparta.newsfeed.controller;

import lombok.Data;

@Data
public class ResponseForm {

    private StatusEnum status;
    private String message;
    private Object data;

    public ResponseForm() {
        this.status = StatusEnum.BAD_REQUEST;
        this.message = null;
        this.data = null;
    }
}
