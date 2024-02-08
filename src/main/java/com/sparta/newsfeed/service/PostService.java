package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.dto.PostRequestDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto savePost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto,user);
        postRepository.save(post);
        return new PostResponseDto(post);

    }

    public List<PostResponseDto> findByCategoryNameToList(String category) {
        return postRepository.findByCategory(category)
                .stream()
                .map(PostResponseDto::new)
                .sorted(Comparator.comparing(PostResponseDto::getModifiedAt).reversed())
                .toList();


    }
}
