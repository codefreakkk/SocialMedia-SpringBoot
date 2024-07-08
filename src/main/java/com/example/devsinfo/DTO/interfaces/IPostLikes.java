package com.example.devsinfo.DTO.interfaces;

import com.example.devsinfo.exceptions.PostLikedException;
import com.example.devsinfo.exceptions.PostNotFoundException;
import com.example.devsinfo.exceptions.UserNotFoundException;

public interface IPostLikes {

    public void addLike(int postId) throws UserNotFoundException, PostNotFoundException, PostLikedException;
    public void removeLike(int postId);
    public void getLikeCount(int postId);

}
