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
import java.util.List;
import java.util.Objects;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto savePost(PostRequestDto requestDto, User user) {
        Post post = new Post();
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

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id에 대한 게시글을 찾을 수 없습니다."));
    }
  
    // 게시글 수정
    @Transactional
    public Post updatePost(Long postId, PostRequestDto dto, User user) {
  //        Post post = checkPWAndGetToDo(postId, user.getPassword());
        Post post = getPost(postId);

        // 아이디 체크
        if(user.getPassword() != null && !Objects.equals(post.getUser().getId(),user.getId()))
            throw new IllegalArgumentException();

        post.setTitle(dto.getTitle());
        post.setContents(dto.getContents());
        return postRepository.save(post);
    }

    // 게시글 삭제
    public void deleteToDo(Long postId, User user) {
        Post post = getPost(postId);

        // 아이디 체크
        if(user.getId() != null && !Objects.equals(post.getUser().getId(),user.getId()))
            throw new IllegalArgumentException();

        postRepository.delete(post);
    }
}
