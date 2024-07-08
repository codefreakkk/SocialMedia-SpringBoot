package com.example.devsinfo.repository;

import com.example.devsinfo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findById(int userId);

    User findByEmail(String email);

}
