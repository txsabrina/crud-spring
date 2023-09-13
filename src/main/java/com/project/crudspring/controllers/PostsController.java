package com.project.crudspring.controllers;

import com.project.crudspring.DTO.PostsDTO;
import com.project.crudspring.DTO.UserDTO;
import com.project.crudspring.services.PostsService;
import com.project.crudspring.services.UserSevice;
import com.project.crudspring.utils.TokenService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostsController {

    private final PostsService postsService;
    private final TokenService tokenService;
    private final UserSevice userSevice;

    public PostsController(PostsService postsService, TokenService tokenService, UserSevice userSevice) {
        this.postsService = postsService;
        this.tokenService = tokenService;
        this.userSevice = userSevice;
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> getAllPosts() {
        try{
            List<PostsDTO> posts = postsService.getAllPosts();
            return ResponseEntity.ok(posts);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable Integer id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        UserDTO user = isValidToken(token);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Optional<PostsDTO> post = postsService.getPostById(id);
            return post.isPresent() ? ResponseEntity.ok(post.get()) : ResponseEntity.notFound().build();  
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }

    @PostMapping("/posts")
    public ResponseEntity<Object> createPost(@RequestBody PostsDTO postDTO, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO user = isValidToken(token);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } 

        try {
            postDTO.setUserId(user);
            PostsDTO createdPost = postsService.createPost(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Integer id, @RequestBody PostsDTO postDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PostsDTO foundPost = postsService.getPostById(id).get();

        UserDTO user = isValidToken(token);
        if(user == null || foundPost.getUserId().getId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } 
        
        try {
            PostsDTO updatedPost = postsService.updatePost(id, postDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
       
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PostsDTO foundPost = postsService.getPostById(id).get();

        UserDTO user = isValidToken(token);
        if(user == null || foundPost.getUserId().getId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            postsService.deletePost(id);
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/posts/user/{id}")
    public ResponseEntity<Object> getPostsByUserId(@PathVariable Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO user = isValidToken(token);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            List<PostsDTO> posts = postsService.getPostsByUserId(id);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }

    private UserDTO isValidToken(String token) {
        try {
            String email = tokenService.validateToken(token);
            UserDTO foundedUser = userSevice.getUserByEmail(email);             
            return foundedUser;
        } catch (Exception e) {
            return null;
        }
    } 
}
