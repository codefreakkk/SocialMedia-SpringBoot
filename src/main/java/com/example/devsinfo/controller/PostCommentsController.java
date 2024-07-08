package com.example.devsinfo.controller;

import com.example.devsinfo.DTO.CommentsDTO;
import com.example.devsinfo.DTO.response.GetAllPostCommentsResponse;
import com.example.devsinfo.services.PostCommentsService;
import com.example.devsinfo.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/posts")
@RestController
public class PostCommentsController {

    @Autowired
    private PostCommentsService postCommentsService;

    @PostMapping(value = "/{postId}/comments")
    public ResponseEntity<GenericResponse> addComment(@PathVariable int postId, @RequestBody CommentsDTO commentsDTO) {
        postCommentsService.addCommentToPost(postId, commentsDTO);
        GenericResponse genericResponse = new GenericResponse("Comment added successfully");
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{postId}/comments")
    public ResponseEntity<GetAllPostCommentsResponse> getAllComments(@PathVariable int postId) {
        GetAllPostCommentsResponse getAllPostCommentsResponse = postCommentsService.getAllPostComments(postId);
        return new ResponseEntity<>(getAllPostCommentsResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/comments/{commentId}")
    public ResponseEntity<GenericResponse> deleteComment(@PathVariable int commentId) {
        postCommentsService.deleteCommentFromPost(commentId);
        GenericResponse genericResponse = new GenericResponse("Comment deleted successfully");
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

}
