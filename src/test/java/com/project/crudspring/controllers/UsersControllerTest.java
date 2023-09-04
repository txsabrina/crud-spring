package com.project.crudspring.controllers;

import com.project.crudspring.DTO.LoginDTO;
import com.project.crudspring.config.TokenService;
import com.project.crudspring.domain.User;
import com.project.crudspring.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class UsersControllerTest {

    private static final Integer ID = 1;
    private static final String NAME = "sabrina";
    private static final String EMAIL = "sabrina@email.com";
    private static final String PASSWORD = "12345678";
    private static final String IMAGE = "image.jpg";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UsersController usersController;

    private User user;
    private LoginDTO loginDTO;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void testCreateUserSuccess() {
        Mockito.when(userRepository.existsByEmail(anyString())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<Object> response = usersController.create(user, Mockito.mock(BindingResult.class));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário criado.", response.getBody());
    }

    @Test
    void testCreateUserEmailExists() {
        Mockito.when(userRepository.existsByEmail(anyString())).thenReturn(true);

        ResponseEntity<Object> response = usersController.create(user, Mockito.mock(BindingResult.class));

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("E-mail já cadastrado.", response.getBody());
    }

    @Test
    void testLoginSuccess() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(user);
        Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        Mockito.when(tokenService.generateToken(any(User.class))).thenReturn("token");

        ResponseEntity<Object> response = usersController.login(loginDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody());
    }

    @Test
    void testLoginUserNotFound() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(null);

        ResponseEntity<Object> response = usersController.login(loginDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("E-mail não cadastrado", response.getBody());
    }
    @Test
    void testLoginInvalidPassword() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(user);
        Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        ResponseEntity<Object> response = usersController.login(loginDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Senha incorreta", response.getBody());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD, IMAGE);
        loginDTO = new LoginDTO(EMAIL, PASSWORD);
    }
}