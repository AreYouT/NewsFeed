package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.dto.PostRequestDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void savePost(PostRequestDto requestDto, User user) {

        postRepository.save(new Post(requestDto,user));

    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findByCategoryNameToList(String category) {
        return postRepository.findByCategory(category)
                .stream()
                .map(PostResponseDto::new)
                .sorted(Comparator.comparing(PostResponseDto::getModifiedAt).reversed())
                .toList();


    }
}
