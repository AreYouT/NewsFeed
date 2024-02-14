package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.request.PostRequestDto;
import com.sparta.newsfeed.dto.request.UpdateRequestDto;
import com.sparta.newsfeed.dto.response.PostListResponseDto;
import com.sparta.newsfeed.dto.response.PostResponseDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.entity.PostLike;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.PostLikeRepository;
import com.sparta.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void savePost(PostRequestDto requestDto, User user) {

        postRepository.save(new Post(requestDto,user));

    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findByCategoryNameToList(String category) {
        return postRepository.findByCategory(category)
                .stream()
                .map(PostListResponseDto::new)
                .sorted(Comparator.comparing(PostListResponseDto::getModifiedAt).reversed())
                .toList();
    }


    @Transactional(readOnly = true)
    public List<PostListResponseDto> getRecommendedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("likeCount").descending());
        List<Post> posts = postRepository.findAll(pageable).getContent();
        return posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }



    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, UpdateRequestDto dto, User user) {
        Post post = getPostById(postId);

        checkUserID(user,post);

        post.update(dto);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = getPostById(postId);

        checkUserID(user,post);

        postRepository.delete(post);
    }

    // 게시글 좋아요
    @Transactional
    public void likePost(Long postId, User user) {
        Post post = getPostById(postId);
        if(postLikeRepository.existsByUserAndPost(user, post)) {
            throw new DataIntegrityViolationException("이미 게시글에 좋아요를 했습니다.");
        }
        post.addLike();
        postLikeRepository.save(new PostLike(user, post));
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id에 대한 게시글을 찾을 수 없습니다."));
    }
    private void checkUserID(User user, Post post) {
        if(user.getId() != null && !Objects.equals(post.getUser().getId(), user.getId()))
            throw new IllegalArgumentException();
    }

    //게시글 조회수 증가
    @Transactional
    public int updateView(Long id) {
        return postRepository.updateView(id);
    }
}