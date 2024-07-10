package com.example.devsinfo.services;

import com.example.devsinfo.DTO.PostDTO;
import com.example.devsinfo.DTO.interfaces.IPostDTO;
import com.example.devsinfo.DTO.interfaces.IPosts;
import com.example.devsinfo.DTO.response.GetAllPostResponse;
import com.example.devsinfo.DTO.response.PostResponse;
import com.example.devsinfo.DTO.response.PostResponseDTO;
import com.example.devsinfo.exceptions.FileUploadException;
import com.example.devsinfo.exceptions.NotAuthorizedUserException;
import com.example.devsinfo.exceptions.PostNotFoundException;
import com.example.devsinfo.exceptions.UserNotFoundException;
import com.example.devsinfo.models.Posts;
import com.example.devsinfo.models.User;
import com.example.devsinfo.repository.PostCommentsDao;
import com.example.devsinfo.repository.PostDao;
import com.example.devsinfo.repository.UserDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostService implements IPosts {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostCommentsDao postCommentsDao;

    @Autowired
    private CloudinaryService cloudinaryService;

    private void validateImage(MultipartFile file) throws FileUploadException {
        String contentType = file.getContentType();
        long size = file.getSize();

        if (file == null) {
            throw new FileUploadException("File not found");
        }

        if (file.getSize() > size) {
            throw new FileUploadException("File must be less than 10 MB");
        }

        List<String> extensions = List.of("image/jpeg", "image/jpg", "image/png");
        if (!extensions.contains(contentType)) {
            throw new FileUploadException("File not supported");
        }
    }

    @Override
    public void createPost(String description, MultipartFile file) throws UserNotFoundException, IOException, FileUploadException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByEmail(email);

        if (user == null || email.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        // validate file
        this.validateImage(file);

        Map uploadResult = cloudinaryService.upload(file);
        String url = (String) uploadResult.get("url");
        System.out.println(url);

        Posts post = Posts.builder()
                .photo(url)
                .description(description)
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

        IPostDTO postDTO = postDao.findPostById(postId);
        List<String> comments = postCommentsDao.findCommentByPostId(postId);

        return PostResponse.builder()
                .id(postDTO.getId())
                .photo(postDTO.getPhoto())
                .description(postDTO.getDescription())
                .likeCount(postDTO.getLikeCount())
                .comments(comments)
                .build();   
    }

    @Override
    public GetAllPostResponse getAllPosts() {

        List<IPostDTO> posts = postDao.findAllPosts();
        List<PostResponseDTO> allPosts = new ArrayList<>();

        for (IPostDTO post : posts) {
            List<String> comments = postCommentsDao.findCommentByPostId(post.getId());
            PostResponseDTO postResponseDTO = PostResponseDTO.builder()
                    .id(post.getId())
                    .photo(post.getPhoto())
                    .description(post.getDescription())
                    .likeCount(post.getLikeCount())
                    .comments(comments)
                    .build();

            allPosts.add(postResponseDTO);
        }

        return GetAllPostResponse.builder()
                .success(true)
                .allPost(allPosts)
                .build();
    }
}
