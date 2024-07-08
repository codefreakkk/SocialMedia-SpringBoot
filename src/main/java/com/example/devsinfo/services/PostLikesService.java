package com.example.devsinfo.services;

import com.example.devsinfo.DTO.interfaces.IPostLikes;
import com.example.devsinfo.exceptions.PostLikedException;
import com.example.devsinfo.exceptions.PostNotFoundException;
import com.example.devsinfo.exceptions.UserNotFoundException;
import com.example.devsinfo.models.PostLikes;
import com.example.devsinfo.models.Posts;
import com.example.devsinfo.models.User;
import com.example.devsinfo.repository.PostDao;
import com.example.devsinfo.repository.PostLikesDao;
import com.example.devsinfo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostLikesService implements IPostLikes {

    @Autowired
    private PostLikesDao postLikesDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PostDao postDao;

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private User getUserByEmail(String email) throws UserNotFoundException {
        User user = userDao.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }

    private Posts getPostByPostId(int postId) throws PostNotFoundException {
        Optional<Posts> post = postDao.findById(postId);

        if (post.isEmpty()) {
            throw new PostNotFoundException("Post not found");
        }

        return post.get();
    }

    @Override
    public void addLike(int postId) throws UserNotFoundException, PostNotFoundException, PostLikedException {
        String email = this.getUserEmail();

        User user = this.getUserByEmail(email);
        Posts post = this.getPostByPostId(postId);

        PostLikes postLikes = postLikesDao.findLikeByPostIdAndUserId(postId, user.getId());
        if (postLikes != null) {
            throw new PostLikedException("Post already liked");
        }
        postLikes = PostLikes.builder()
                .posts(post)
                .user(user)
                .build();

        postLikesDao.save(postLikes);
    }

    @Override
    public void removeLike(int postId) throws UserNotFoundException, PostNotFoundException{
        String email = this.getUserEmail();
        User user = this.getUserByEmail(email);

        postLikesDao.deleteLikeByPostIdAndUserId(postId, user.getId());
    }

    @Override
    public void getLikeCount(int postId) {

    }
}
