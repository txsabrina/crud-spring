package com.project.crudspring.controllers;

import com.project.crudspring.DTO.LoginDTO;
import com.project.crudspring.DTO.UserDTO;
import com.project.crudspring.services.UserSevice;
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
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário criado.", response.getBody());
    }

    @Test
    void testLoginSuccess() throws Exception {
        Mockito.when(service.login(credentials)).thenReturn("Token");

        ResponseEntity<Object> response = controller.login(credentials, null);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Token", response.getBody());
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