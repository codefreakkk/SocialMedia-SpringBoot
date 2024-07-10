package com.example.devsinfo.DTO.interfaces;

import com.example.devsinfo.DTO.PostDTO;
import com.example.devsinfo.DTO.response.GetAllPostResponse;
import com.example.devsinfo.DTO.response.PostResponse;
import com.example.devsinfo.exceptions.FileUploadException;
import com.example.devsinfo.exceptions.NotAuthorizedUserException;
import com.example.devsinfo.exceptions.PostNotFoundException;
import com.example.devsinfo.exceptions.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPosts {
    public void createPost(String description, MultipartFile file) throws UserNotFoundException, IOException, FileUploadException;
    public PostResponse updatePost(int postId, PostDTO postDTO);
    public void deletePost(int postId) throws PostNotFoundException, NotAuthorizedUserException;
    public PostResponse getPostById(int postId) throws PostNotFoundException;
    public GetAllPostResponse getAllPosts();
}
