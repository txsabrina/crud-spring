package com.project.crudspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.crudspring.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Boolean existsByEmail(String email);
    public User findByEmail(String email);
}
