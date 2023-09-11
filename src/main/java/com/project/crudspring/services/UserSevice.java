package com.project.crudspring.services;

import com.project.crudspring.DTO.LoginDTO;
import com.project.crudspring.DTO.UserDTO;
import com.project.crudspring.domain.User;
import com.project.crudspring.repositories.UserRepository;
import com.project.crudspring.utils.TokenService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserSevice implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UserRepository repository;
    private final ModelMapper mapper;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;

    @Autowired
    public UserSevice(UserRepository repository, ModelMapper mapper, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public Object create(UserDTO user) {

        User modifyUser = mapper.map(user, User.class);

        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        modifyUser.setPassword(encodedPassword);

        User savedUser = repository.save(modifyUser);

        return mapper.map(savedUser, UserDTO.class);

    }

    public String login(LoginDTO credentials) throws Exception {
        User user = repository.findByEmail(credentials.getEmail());

        System.out.println("USER " + user);

        if (user == null) {
            throw new Exception("E-mail não cadastrado");
        }

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new Exception("Senha incorreta");
        }

        return tokenService.generateToken(user);

    }

    public UserDTO getUser(Integer id) throws Exception {
        Optional<User> userFound = repository.findById(id);
        User user = userFound.orElseThrow(() -> new Exception("Usuário não encontrado"));
        return mapper.map(user, UserDTO.class);
    }

    public UserDTO getUserByEmail(String email) throws Exception {
        User user = repository.findByEmail(email);
        return user != null ? mapper.map(user, UserDTO.class) : null;
    }

}
