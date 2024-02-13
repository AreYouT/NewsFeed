package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.entity.PostLike;
import com.sparta.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    int countByPost(Post post);
    boolean existsByUserAndPost(User user, Post post);
}
