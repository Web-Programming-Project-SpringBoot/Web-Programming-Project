package com.example.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
