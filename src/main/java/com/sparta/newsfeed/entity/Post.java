package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.request.PostRequestDto;
import com.sparta.newsfeed.dto.request.PostUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = true)
    private Long likeCount = 0L;

    @Column(nullable = true)
    private Long viewCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(PostRequestDto requestDto, User user) {
        this.category = requestDto.getCategory();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void update(PostUpdateRequestDto dto) {
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }

    public void addLike(){
        this.likeCount++;
    }
}
