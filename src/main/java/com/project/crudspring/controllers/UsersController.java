package com.project.crudspring.controllers;

import com.project.crudspring.DTO.UserDTO;
import com.project.crudspring.services.UserSevice;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UsersController {

    private final UserSevice service;

    @PostMapping("/signup")
    public ResponseEntity<Object> create(@Valid @RequestBody UserDTO user, BindingResult result) {

        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldError().getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(service.create(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDTO credentials) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.login(credentials));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getUser(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
