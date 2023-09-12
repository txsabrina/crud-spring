package com.project.crudspring.utils;

import com.project.crudspring.DTO.UserDTO;

import lombok.Data;

@Data
public class Auth {
    private String token;
    private UserDTO user; 

    public Auth(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }
}

