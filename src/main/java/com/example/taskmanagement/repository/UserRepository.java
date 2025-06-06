package com.example.taskmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.taskmanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User getUserById(Long userId);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findByIdWithRoles(Long id);
    
  
}
