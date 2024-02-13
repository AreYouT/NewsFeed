package com.sparta.newsfeed.dto.response;

import com.sparta.newsfeed.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private LocalDateTime date;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContents();
        this.date = comment.getCreatedAt();
    }
}
