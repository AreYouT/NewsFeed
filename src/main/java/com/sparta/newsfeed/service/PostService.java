package com.sparta.newsfeed.service;

import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.repository.PostRepository;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class PostService {

    private PostRepository postRepository;

    public List<Post> getRecommendedPosts() {
        return postRepository.findAll(Sort.by("createdAt").descending());
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("안녕"));
    }
}
