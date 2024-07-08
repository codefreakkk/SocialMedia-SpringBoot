package com.example.devsinfo.repository;

import com.example.devsinfo.DTO.CommentsDTO;
import com.example.devsinfo.models.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentsDao extends JpaRepository<PostComments, Integer> {

    @Query(value = "SELECT * FROM post_comments p WHERE p.post_id = :postId", nativeQuery = true)
    List<PostComments> findByPostId(@Param("postId") int postId);

}
