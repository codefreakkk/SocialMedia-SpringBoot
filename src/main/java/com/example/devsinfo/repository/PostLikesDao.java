package com.example.devsinfo.repository;

import com.example.devsinfo.models.PostLikes;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikesDao extends JpaRepository<PostLikes, Integer> {

    @Query(value = "DELETE FROM post_likes pl WHERE pl.post_id = :postId AND pl.user_id = :userId", nativeQuery = true)
    void deleteLikeByPostIdAndUserId(@Param("postId") int postId, @Param("userId") int userId);

    @Query(value = "SELECT * FROM post_likes pl WHERE pl.post_id = :postId AND pl.user_id = :userId", nativeQuery = true)
    PostLikes findLikeByPostIdAndUserId(@Param("postId") int postId, @Param("userId") int userId);
}
