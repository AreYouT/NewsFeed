package com.sparta.newsfeed.dto.response;

import com.sparta.newsfeed.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private Long id;
    private String title;
    private Long likeCount;
    private Long viewCount;
    private String category;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();
        this.category = post.getCategory();
        this.username = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
