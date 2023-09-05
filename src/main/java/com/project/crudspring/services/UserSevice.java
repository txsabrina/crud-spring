package com.project.crudspring.services;

import com.project.crudspring.DTO.UserDTO;
import com.project.crudspring.config.TokenService;
import com.project.crudspring.domain.User;
import com.project.crudspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserSevice {

    private final UserRepository repository;
    private final ModelMapper mapper;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;


    public Object create(UserDTO user) {

        User modifyUser = mapper.map(user, User.class);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = repository.save(modifyUser);

        return mapper.map(savedUser, UserDTO.class);

    }

    public String login(UserDTO credentials) throws Exception {
        User user = repository.findByEmail(credentials.getEmail());

        if (user == null) {
            throw new Exception("E-mail não cadastrado");
        }

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new Exception("Senha incorreta");
        }

        return tokenService.generateToken(user);
    }

    public UserDTO getUser(Integer id) throws Exception {
        Optional<User> findedUser = repository.findById(id);
        User user = findedUser.orElseThrow(() -> new Exception("Usuário não encontrado"));
        return mapper.map(user, UserDTO.class);
    }

}
