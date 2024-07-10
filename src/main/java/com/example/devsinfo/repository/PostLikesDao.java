package com.example.devsinfo.repository;

import com.example.devsinfo.DTO.interfaces.IPostLikeCountDTO;
import com.example.devsinfo.models.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostLikesDao extends JpaRepository<PostLikes, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM post_likes pl WHERE pl.post_id = :postId AND pl.user_id = :userId", nativeQuery = true)
    void deleteLikeByPostIdAndUserId(@Param("postId") int postId, @Param("userId") int userId);

    @Query(value = "SELECT * FROM post_likes pl WHERE pl.post_id = :postId AND pl.user_id = :userId", nativeQuery = true)
    PostLikes findLikeByPostIdAndUserId(@Param("postId") int postId, @Param("userId") int userId);

    @Query(value = "SELECT count(*) as likeCount FROM post_likes pl WHERE pl.post_id = :postId GROUP BY pl.post_id", nativeQuery = true)
    IPostLikeCountDTO findLikeCountByPostId(@Param("postId") int postId);
}
