package com.project.crudspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.crudspring.model.User;
import com.project.crudspring.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UsersController {

    private UserRepository repository;

    @PostMapping("/singup")
    public ResponseEntity<Object> create(@Valid @RequestBody User user, BindingResult result) {

        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldError().getDefaultMessage());
        }
        if(repository.existsByEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já cadastrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(user));
    }
}
