package com.example.linkedinProject.postsService.service;

import com.example.linkedinProject.postsService.entity.Post;
import com.example.linkedinProject.postsService.entity.PostLikes;
import com.example.linkedinProject.postsService.exception.BadRequestException;
import com.example.linkedinProject.postsService.exception.ResourceNotFoundException;
import com.example.linkedinProject.postsService.repository.PostLikeRepository;
import com.example.linkedinProject.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void likePost(Long postId) {
        Long userId = 1L;
        log.info("User with id: {} liking the post with id: {}",userId,postId);

        postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with id: {}"+ postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);

        if(hasAlreadyLiked) throw new BadRequestException("You cannot like the post again");

        PostLikes postLikes = new PostLikes();
        postLikes.setPostId(postId);
        postLikes.setUserId(userId);
        postLikeRepository.save(postLikes);

    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1L;
        log.info("User with id: {} liking the post with id: {}",userId,postId);

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with id: {}"+ postId));

        boolean hasAlreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);

        if(!hasAlreadyLiked) throw new BadRequestException("You cannot unlike the post again");
        postLikeRepository.deleteByUserIdAndPostId(userId,postId);
    }
}