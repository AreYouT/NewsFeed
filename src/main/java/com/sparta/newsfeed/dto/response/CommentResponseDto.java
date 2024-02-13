package com.sparta.newsfeed.dto.response;

import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private String mbti;
    private LocalDateTime date;

    public CommentResponseDto(Comment comment, User user) {
        this.commentId = comment.getId();
        this.content = comment.getContents();
        this.mbti = user.getMbti();
        this.date = comment.getCreatedAt();
    }
}
