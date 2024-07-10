package com.example.devsinfo.controller;

import com.example.devsinfo.DTO.PostLikeCountDTO;
import com.example.devsinfo.DTO.interfaces.IPostLikeCountDTO;
import com.example.devsinfo.services.PostLikesService;
import com.example.devsinfo.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/posts")
@RestController
public class PostLikesController {

    @Autowired
    private PostLikesService postLikesService;

    @PostMapping(value = "/{postId}/likes")
    public ResponseEntity<GenericResponse> addLike(@PathVariable int postId) {
        postLikesService.addLike(postId);
        GenericResponse response = new GenericResponse("Like added");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{postId}/likes")
    public ResponseEntity<GenericResponse> removeLike(@PathVariable int postId) {
        postLikesService.removeLike(postId);
        GenericResponse genericResponse = new GenericResponse("Like removed");
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}/likes")
    public ResponseEntity<PostLikeCountDTO> getPostLikesCount(@PathVariable int postId) {
        PostLikeCountDTO postLikeCountDTO = postLikesService.getLikeCount(postId);
        return new ResponseEntity<>(postLikeCountDTO, HttpStatus.OK);
    }

}
