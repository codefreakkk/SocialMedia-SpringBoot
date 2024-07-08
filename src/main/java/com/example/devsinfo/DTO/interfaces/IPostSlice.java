package com.example.devsinfo.DTO.interfaces;

import com.example.devsinfo.DTO.PostDTO;
import com.example.devsinfo.DTO.response.GetAllPostResponse;
import com.example.devsinfo.DTO.response.PostResponse;
import com.example.devsinfo.exceptions.NotAuthorizedUserException;
import com.example.devsinfo.exceptions.PostNotFoundException;
import com.example.devsinfo.exceptions.UserNotFoundException;

public interface IPostSlice {
    public void createPost(String authHeader, PostDTO postDTO) throws UserNotFoundException;
    public PostResponse updatePost(int postId, PostDTO postDTO);
    public void deletePost(int postId) throws PostNotFoundException, NotAuthorizedUserException;
    public PostResponse getPostById(int postId) throws PostNotFoundException;
    public GetAllPostResponse getAllPosts();
}
