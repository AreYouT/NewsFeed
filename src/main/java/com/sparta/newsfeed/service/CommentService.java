package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.request.CommentRequestDto;
import com.sparta.newsfeed.dto.response.CommentResponseDto;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.entity.Post;
import com.sparta.newsfeed.entity.User;
import com.sparta.newsfeed.repository.CommentRepository;
import com.sparta.newsfeed.repository.PostRepository;
import com.sparta.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(User user,  CommentRequestDto requestDto, Long postId){
        User findUser = findUserByUsername(user);

        Post findPost = findPostById(postId);

        Comment comment = new Comment(requestDto, findUser, findPost);

        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long post_id){
        List<Comment> comments = commentRepository.findAllByPostId(post_id);
        List<CommentResponseDto> commentResponseDtoList = new LinkedList<>();

        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment, comment.getUser());
            commentResponseDtoList.add(commentResponseDto);
        }

        return commentResponseDtoList;
    }

    @Transactional
    public void updateComment(User user, CommentRequestDto requestDto, Long postId, Long commentId) {
        User findUser = findUserByUsername(user);

        Post findPost = findPostById(postId);

        Comment findComment = findCommentById(commentId);

        findComment.update(requestDto);
    }

    @Transactional
    public void deleteComment(User user, Long postId, Long commentId) {
        User findUser = findUserByUsername(user);

        Post findPost = findPostById(postId);

        Comment findComment = findCommentById(commentId);

        commentRepository.delete(findComment);
    }

    private User findUserByUsername(User user){
        return userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유저가 존재하지 않습니다.")
        );
    }

    private Post findPostById(Long postId){
        return postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글을 찾지 못했습니다.")
        );
    }

    private Comment findCommentById(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾지 못했습니다.")
        );
    }
}
