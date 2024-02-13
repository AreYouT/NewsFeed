package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.request.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(CommentRequestDto requestDto, User user, Post post) {
        this.contents = requestDto.getContent();
        this.user = user;
        this.post = post;
    }


    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContent();
    }
}
