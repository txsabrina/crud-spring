package com.project.crudspring.controllers;

import com.project.crudspring.DTO.PostsDTO;
import com.project.crudspring.services.PostsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PostsDTO>> getAllPosts() {
        List<PostsDTO> posts = postsService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostsDTO> getPostById(@PathVariable Integer id) {
        Optional<PostsDTO> post = postsService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<PostsDTO> createPost(@RequestBody PostsDTO postDTO) {
        PostsDTO createdPost = postsService.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostsDTO> updatePost(@PathVariable Integer id, @RequestBody PostsDTO postDTO) {
        PostsDTO updatedPost = postsService.updatePost(id, postDTO);
        return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postsService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostsDTO>> getPostsByUserId(@PathVariable Integer id) {
        List<PostsDTO> posts = postsService.getPostsByUserId(id);
        return ResponseEntity.ok(posts);
    }

}
