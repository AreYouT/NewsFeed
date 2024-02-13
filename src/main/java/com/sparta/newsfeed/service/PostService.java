package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.request.PostRequestDto;
import com.sparta.newsfeed.dto.request.UpdateRequestDto;
import com.sparta.newsfeed.dto.response.PostResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto savePost(PostRequestDto requestDto, User user) {
        Post post = new Post(requestDto, user);
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

    public List<Post> getRecommendedPosts() {
        return postRepository.findAll(Sort.by("likeCount").descending());
    }

    // 게시글 수정
    public Post updatePost(Long postId, UpdateRequestDto dto, User user) {
//        Post post = checkPWAndGetToDo(postId, user.getPassword());
        Post post = getPostById(postId);

        checkUserID(user,post);

        post.update(dto);
        return postRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long postId, User user) {
        Post post = getPostById(postId);

        checkUserID(user,post);

        postRepository.delete(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id에 대한 게시글을 찾을 수 없습니다."));
    }
    private void checkUserID(User user, Post post) {
        if(user.getId() != null && !Objects.equals(post.getUser().getId(), user.getId()))
            throw new IllegalArgumentException();
    }
}