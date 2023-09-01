package com.project.crudspring.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.crudspring.model.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                        .withIssuer("crud-api")
                        .withSubject(user.getEmail())
                        .withExpiresAt(getExpirationDate())
                        .sign(algorithm);
            return token;
        } catch (JWTCreationException e){
            throw new RuntimeException("Error ao gerar token", e);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String verify = JWT.require(algorithm)
                        .withIssuer("crud-api")
                        .build()
                        .verify(token)
                        .getSubject();
            return verify;
        } catch (JWTVerificationException e){
            return "";
        }
    }

    private Instant getExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
