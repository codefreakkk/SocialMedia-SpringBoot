package com.example.devsinfo.services;

import com.example.devsinfo.DTO.PostDTO;
import com.example.devsinfo.DTO.interfaces.IPostSlice;
import com.example.devsinfo.DTO.response.GetAllPostResponse;
import com.example.devsinfo.DTO.response.PostResponse;
import com.example.devsinfo.exceptions.NotAuthorizedUserException;
import com.example.devsinfo.exceptions.PostNotFoundException;
import com.example.devsinfo.exceptions.UserNotFoundException;
import com.example.devsinfo.models.Posts;
import com.example.devsinfo.models.User;
import com.example.devsinfo.repository.PostDao;
import com.example.devsinfo.repository.UserDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostSlice {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createPost(String authHeader, PostDTO postDTO) throws UserNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByEmail(email);

        if (user == null || email.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Posts post = Posts.builder()
                .photo(postDTO.getPhoto())
                .description(postDTO.getDescription())
                .user(user)
                .build();

        postDao.save(post);
    }

    @Override
    public PostResponse updatePost(int postId, PostDTO postDTO) {
        Optional<Posts> post = postDao.findById(postId);
        if (post.isEmpty()) {
            throw new PostNotFoundException("Post not found");
        }
        Posts currentPost = Posts.builder()
                .id(postId)
                .photo(postDTO.getPhoto())
                .description(postDTO.getDescription())
                .user(post.get().getUser())
                .build();

        postDao.save(currentPost);

        return PostResponse.builder()
                .id(postId)
                .photo(currentPost.getPhoto())
                .description(currentPost.getDescription())
                .build();
    }

    @Override
    public void deletePost(int postId) throws PostNotFoundException, NotAuthorizedUserException {
        Optional<Posts> posts = postDao.findById(postId);
        if (posts.isEmpty()) {
            throw new PostNotFoundException("Post not found");
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByEmail(email);
        if (user != null && user.getId() == posts.get().getId()) {
            postDao.deleteById(postId);
        }
        throw new NotAuthorizedUserException("Not authorized user to delete this post");
    }

    @Override
    public PostResponse getPostById(int postId) throws PostNotFoundException {
        Optional<Posts> post = postDao.findById(postId);
        if (post.isEmpty()) {
            throw new PostNotFoundException("Post not found");
        }

        Posts currentPost = post.get();
        return PostResponse.builder()
                .id(currentPost.getId())
                .photo(currentPost.getPhoto())
                .description(currentPost.getDescription())
                .build();
    }

    @Override
    public GetAllPostResponse getAllPosts() {
        List<Posts> posts = postDao.findAll();
        List<PostDTO> allPosts = posts.stream().map(post ->
                this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        return GetAllPostResponse.builder()
                .success(true)
                .allPost(allPosts)
                .build();
    }
}
