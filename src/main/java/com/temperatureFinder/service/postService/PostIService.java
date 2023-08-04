package com.temperatureFinder.service.postService;

import com.temperatureFinder.dto.PostDto;

import java.util.List;

public interface PostIService {

     PostDto createPost (PostDto postDto);
     List<PostDto> getAllPosts();
     PostDto getPostById(long id);
     PostDto UpdateById(PostDto postDto , long id);
     void deletePostById(long id);
}
