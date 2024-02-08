package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private final String category;
    private final String title;
    private final String contents;
    private final Long likeCount;
    private final Long viewCount;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.category = post.getCategory();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();
    }
}
