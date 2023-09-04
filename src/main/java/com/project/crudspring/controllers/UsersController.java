package com.project.crudspring.controllers;

import com.project.crudspring.DTO.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.crudspring.DTO.LoginDTO;
import com.project.crudspring.config.TokenService;
import com.project.crudspring.domain.User;
import com.project.crudspring.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UsersController {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    private ModelMapper mapper;

    @PostMapping("/signup")
    public ResponseEntity<Object> create(@Valid @RequestBody User user, BindingResult result) {

        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldError().getDefaultMessage());
        }
        if(repository.existsByEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já cadastrado.");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        mapper.map(repository.save(user), UserDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body("Usuário criado.");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO credentials) {
        User user = repository.findByEmail(credentials.getEmail());
        
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não cadastrado");
        }
        if(!passwordEncoder.matches(credentials.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
        }

        
        String token = tokenService.generateToken(user);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
