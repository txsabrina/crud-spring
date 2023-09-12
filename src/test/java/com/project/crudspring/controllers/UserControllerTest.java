package com.project.crudspring.controllers;

import com.project.crudspring.DTO.LoginDTO;
import com.project.crudspring.DTO.UserDTO;
import com.project.crudspring.services.UserSevice;
import com.project.crudspring.utils.Auth;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserControllerTest {

    private static final Integer ID = 1;
    private static final String NAME = "sabrina";
    private static final String EMAIL = "sabrina@email.com";
    private static final String PASSWORD = "12345678";

    @InjectMocks
    private UsersController controller;

    @Mock
    private UserSevice service;

    @Mock
    private BindingResult result;

    @Mock
    private HttpServletResponse response;

    private UserDTO user;
    private LoginDTO credentials;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void testCreateUserSuccess() {
        Mockito.when(result.hasErrors()).thenReturn(false);
        Mockito.when(service.create(user)).thenReturn("Usuário criado.");


        ResponseEntity<Object> response = controller.create(user,result);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuário criado.", response.getBody());
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Mock o serviço para retornar um objeto Auth com um token
        Auth auth = new Auth("Token", null); // O segundo argumento é o usuário, que é nulo no seu caso
        Mockito.when(service.login(credentials)).thenReturn(auth.getToken());
    
        ResponseEntity<Object> responseEntity = controller.login(credentials, response);
    
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Token", ((Auth) responseEntity.getBody()).getToken());
    }

    @Test
    void testGetUserSuccess() throws Exception {
        Mockito.when(service.getUser(ID)).thenReturn(user);

        ResponseEntity<Object> response = controller.getUser(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    private void startUser() {
        user = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        credentials = new LoginDTO(EMAIL, PASSWORD);
    }
}