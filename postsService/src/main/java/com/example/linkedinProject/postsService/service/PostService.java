package com.example.linkedinProject.postsService.service;

import com.example.linkedinProject.postsService.dto.PostCreateRequestDto;
import com.example.linkedinProject.postsService.dto.PostDto;
import com.example.linkedinProject.postsService.entity.Post;
import com.example.linkedinProject.postsService.exception.ResourceNotFoundException;
import com.example.linkedinProject.postsService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long userId) {
        log.info("Creating post for user with id: {}",userId);
        Post post = modelMapper.map(postCreateRequestDto,Post.class);
        post.setUserId(userId);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.info("Getting post with id: {}",postId);
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found woth id: " + postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all posts of a user with id: {}",userId);
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map((element) -> modelMapper.map(element, PostDto.class)).collect(Collectors.toList());
    }
}
