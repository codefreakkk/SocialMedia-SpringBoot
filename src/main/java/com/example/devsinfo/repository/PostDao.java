package com.example.devsinfo.repository;

import com.example.devsinfo.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends JpaRepository<Posts, Integer> {
}
