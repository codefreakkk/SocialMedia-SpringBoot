package com.example.devsinfo.repository;

import com.example.devsinfo.DTO.interfaces.IPostDTO;
import com.example.devsinfo.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface PostDao extends JpaRepository<Posts, Integer> {

    @Query(value = "SELECT p.id as id, p.photo, p.description, count(pl.post_id) as likeCount FROM posts p LEFT JOIN post_likes pl ON p.id = pl.post_id GROUP BY p.id", nativeQuery = true)
    List<IPostDTO> findAllPosts();

    @Query(value = "SELECT p.id as id, p.photo, p.description, count(pl.post_id) as likeCount FROM posts p LEFT JOIN post_likes pl ON p.id = pl.post_id WHERE pl.post_id = :postId GROUP BY p.id", nativeQuery = true)
    IPostDTO findPostById(@Param("postId") int postId);
}
