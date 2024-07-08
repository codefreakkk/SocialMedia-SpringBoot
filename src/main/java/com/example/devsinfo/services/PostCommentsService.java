package com.example.devsinfo.services;

import com.example.devsinfo.DTO.CommentsDTO;
import com.example.devsinfo.DTO.interfaces.IPostCommentsSlice;
import com.example.devsinfo.DTO.response.GetAllPostCommentsResponse;
import com.example.devsinfo.exceptions.CommentNotFoundException;
import com.example.devsinfo.exceptions.PostNotFoundException;
import com.example.devsinfo.models.PostComments;
import com.example.devsinfo.models.Posts;
import com.example.devsinfo.repository.PostCommentsDao;
import com.example.devsinfo.repository.PostDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostCommentsService implements IPostCommentsSlice {

    @Autowired
    private PostDao postDao;

    @Autowired
    private PostCommentsDao postCommentsDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addCommentToPost(int postId, CommentsDTO commentsDTO) throws PostNotFoundException {
        Optional<Posts> post = postDao.findById(postId);
        if (post.isEmpty()) {
            throw new PostNotFoundException("Post not found");
        }
        PostComments postComments = PostComments.builder()
                .commentContent(commentsDTO.getCommentContent())
                .posts(post.get())
                .build();

        postCommentsDao.save(postComments);
    }

    @Override
    public void deleteCommentFromPost(int commentId) throws CommentNotFoundException {
        Optional<PostComments> postComment = postCommentsDao.findById(commentId);
        if (postComment.isEmpty()) {
            throw new CommentNotFoundException("Comment not found");
        }
        postCommentsDao.deleteById(commentId);
    }

    @Override
    public GetAllPostCommentsResponse getAllPostComments(int postId) {
        List<PostComments> postComments = postCommentsDao.findByPostId(postId);
        List<CommentsDTO> allPostComments = postComments.stream().map(comments ->
                this.modelMapper.map(comments, CommentsDTO.class)).collect(Collectors.toList());

        return GetAllPostCommentsResponse.builder()
                .sucess(true)
                .comments(allPostComments)
                .build();
    }

}
