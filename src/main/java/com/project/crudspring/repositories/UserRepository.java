package com.project.crudspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.crudspring.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Boolean existsByEmail(String email);
    public User findByEmail(String email);
}
