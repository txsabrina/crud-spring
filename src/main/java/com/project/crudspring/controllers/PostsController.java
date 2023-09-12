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
    public ResponseEntity<PostsDTO> getPostById(@PathVariable Integer id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");

        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO foundUser = userSevice.getUser(id);
        
        UserDTO user = isValidToken(token, foundUser);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } 

        Optional<PostsDTO> post = postsService.getPostById(id);
        
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/posts")
    public ResponseEntity<PostsDTO> createPost(@RequestBody PostsDTO postDTO, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO user = isValidToken(token, postDTO.getUserId());
         if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } 

        postDTO.setUserId(user);

        PostsDTO createdPost = postsService.createPost(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostsDTO> updatePost(@PathVariable Integer id, @RequestBody PostsDTO postDTO, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");

        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO foundUser = userSevice.getUser(id);
        
        UserDTO user = isValidToken(token, foundUser);
        if(user == null || id != user.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } 
        
        PostsDTO updatedPost = postsService.updatePost(id, postDTO);
        return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");

        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO foundUser = userSevice.getUser(id);
        
        UserDTO user = isValidToken(token, foundUser);
        if(user == null || id != user.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        postsService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/user/{id}")
    public ResponseEntity<List<PostsDTO>> getPostsByUserId(@PathVariable Integer id) {
        List<PostsDTO> posts = postsService.getPostsByUserId(id);
        return ResponseEntity.ok(posts);
    }

    private UserDTO isValidToken(String token, UserDTO user) {
        try {
            String email = tokenService.validateToken(token);
            UserDTO foundedUser = userSevice.getUserByEmail(email);             
            return foundedUser;
        } catch (Exception e) {
            return null;
        }
    } 
}
