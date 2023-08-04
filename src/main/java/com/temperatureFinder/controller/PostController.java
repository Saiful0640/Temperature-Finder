package com.temperatureFinder.controller;

import com.temperatureFinder.dto.PostDto;
import com.temperatureFinder.model.Post;
import com.temperatureFinder.service.postService.PostIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    PostIService postIService;

    @PostMapping(value = "/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){

        PostDto postSave = postIService.createPost(postDto);

        return  new ResponseEntity<>(postSave, HttpStatus.CREATED);
    }

    @GetMapping(value = "/posts")
    public ResponseEntity<List<PostDto>> getAllPost(){

        List<PostDto> post = postIService.getAllPosts();
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "post/{id}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){

        PostDto postDtoOne = postIService.getPostById(id);

        return  ResponseEntity.ok(postDtoOne);
    }

    @PutMapping(value = "post/{id}")
    public ResponseEntity<PostDto> updatePost (@RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto postDtoUpdate = postIService.UpdateById(postDto,id);

        return new ResponseEntity<>(postDtoUpdate, HttpStatus.OK);
    }

    @DeleteMapping(value = "post/{id}")
    public ResponseEntity <String> postDeletById(@PathVariable(name = "id") long id){
         postIService.deletePostById(id);

         return new ResponseEntity<>("Post Delete Successfully", HttpStatus.OK);
    }
}
