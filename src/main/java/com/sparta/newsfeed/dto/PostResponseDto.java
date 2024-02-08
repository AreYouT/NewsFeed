package com.sparta.newsfeed.dto;


import com.sparta.newsfeed.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long id;

    private String title;

    private String contents;

    private Long likeCount;

    private Long viewCount;

    private String category;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();

        this.category = post.getCategory();
        this.username = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
