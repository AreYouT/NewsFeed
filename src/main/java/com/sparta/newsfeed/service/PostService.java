package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.PostListResponseDto;
import com.sparta.newsfeed.dto.PostRequestDto;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void savePost(PostRequestDto requestDto, User user) {

        postRepository.save(new Post(requestDto,user));

    }

    public List<PostListResponseDto> findByCategoryNameToList(String category) {
        return postRepository.findByCategory(category)
                .stream()
                .map(PostListResponseDto::new)
                .sorted(Comparator.comparing(PostListResponseDto::getModifiedAt).reversed())
                .toList();


    }

    // 게시글 수정
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
