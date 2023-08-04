package com.temperatureFinder.service.postService.postServiceImpl;

import com.temperatureFinder.dto.PostDto;
import com.temperatureFinder.exception.ResourceFoundException;
import com.temperatureFinder.model.Post;
import com.temperatureFinder.repository.PostRepository;
import com.temperatureFinder.service.postService.PostIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostIService {

    @Autowired
    PostRepository postRepo;



    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);

        Post newPost = postRepo.save(post);

        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepo.findAll();

        return posts.stream().map(post -> (mapToDto(post))).collect(Collectors.toList()) ;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceFoundException("post", "id", id));

        return mapToDto(post);
    }

    @Override
    public PostDto UpdateById(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceFoundException("post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepo.save(post);

        return mapToDto(updatePost);

    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceFoundException("post", "id", id));

         postRepo.delete(post);

    }


    private PostDto mapToDto(Post post){

        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());

        return postDto;

    }

    private Post mapToEntity (PostDto postDto){
        Post post = new Post();

        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        return post;
    }
}
