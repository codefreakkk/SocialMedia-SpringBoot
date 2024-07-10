package com.example.devsinfo.controller;

import com.example.devsinfo.DTO.PostDTO;
import com.example.devsinfo.DTO.response.GetAllPostResponse;
import com.example.devsinfo.DTO.response.PostResponse;
import com.example.devsinfo.exceptions.UserNotFoundException;
import com.example.devsinfo.services.PostService;
import com.example.devsinfo.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/v1/posts")
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<GenericResponse> createPost(@RequestParam("file") MultipartFile file, @RequestParam("description") String description) throws UserNotFoundException, IOException {
        postService.createPost(description, file);
        GenericResponse genericResponse = new GenericResponse("Post created");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GetAllPostResponse> getAllPost() {
        GetAllPostResponse postResponse = postService.getAllPosts();
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable int postId) {
        PostResponse postResponse = postService.getPostById(postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable int postId, @RequestBody PostDTO postDTO) {
        PostResponse postResponse = postService.updatePost(postId, postDTO);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<GenericResponse> deletePost(@PathVariable int postId) {
        GenericResponse genericResponse = new GenericResponse("Post deleted");
        postService.deletePost(postId);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

}
