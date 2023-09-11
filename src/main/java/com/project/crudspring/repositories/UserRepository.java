package com.project.crudspring.repositories;

import com.project.crudspring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Boolean existsByEmail(String email);
    public User findByEmail(String email);
}
