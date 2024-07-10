package com.example.devsinfo.DTO.interfaces;

import com.example.devsinfo.DTO.CommentsDTO;
import com.example.devsinfo.DTO.response.GetAllPostCommentsResponse;
import com.example.devsinfo.exceptions.CommentNotFoundException;
import com.example.devsinfo.exceptions.PostNotFoundException;

public interface IPostComments {
    public void addCommentToPost(int postId, CommentsDTO commentsDTO) throws PostNotFoundException;
    public void deleteCommentFromPost(int commentId) throws CommentNotFoundException;
    public GetAllPostCommentsResponse getAllPostComments(int postId);
}
