package com.example.devsinfo.DTO.interfaces;

import com.example.devsinfo.DTO.CommentsDTO;
import com.example.devsinfo.DTO.response.GetAllPostCommentsResponse;
import com.example.devsinfo.exceptions.CommentNotFoundException;
import com.example.devsinfo.exceptions.PostNotFoundException;
import com.example.devsinfo.models.PostComments;

import java.util.List;

public interface IPostCommentsSlice {
    public void addCommentToPost(int postId, CommentsDTO commentsDTO) throws PostNotFoundException;
    public void deleteCommentFromPost(int commentId) throws CommentNotFoundException;
    public GetAllPostCommentsResponse getAllPostComments(int postId);
}
