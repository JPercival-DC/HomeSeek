package com.homeseek26.homeseeksystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeseek26.homeseeksystem.entity.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(String role);
    
    boolean existsByEmail(String email);
}