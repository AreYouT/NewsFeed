package com.sparta.newsfeed.service;


import com.sparta.newsfeed.dto.PostRequestDto;
import com.sparta.newsfeed.dto.PostResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;


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

    public List<Post> getRecommendedPosts() {
        return postRepository.findAll(Sort.by("likeCount").descending());
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id에 대한 게시글을 찾을 수 없습니다."));
    }

}