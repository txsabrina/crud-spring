package com.project.crudspring.repositories;

import com.project.crudspring.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {
    Posts findByTitle(String title);
    List<Posts> findAllByUserId(Integer id);
}
