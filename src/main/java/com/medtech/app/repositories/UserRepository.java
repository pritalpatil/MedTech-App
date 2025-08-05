package com.medtech.app.repositories;

import com.medtech.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find user by email
    Optional<User> findByEmail(String email);
    
    // Check if user exists by email
    boolean existsByEmail(String email);
    
    // Find user by email and password (for login)
    Optional<User> findByEmailAndPassword(String email, String password);
} 