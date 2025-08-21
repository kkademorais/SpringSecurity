package com.SpringSecurityDemo.SpringSecurity.infra.security;

import com.SpringSecurityDemo.SpringSecurity.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

// Geração dos tokens
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;  // Salt para Hashing único -> Buscar das variáveis de ambiente (.env)

        // Create JWT
    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth0")   // Emissor do token
                    .withSubject(user.getLogin())   // Quem vai receber o token -> Permite identificar o user durante requisição
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        }
        catch (JWTCreationException e){
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String verifyToken(String token){
        DecodedJWT decodedJWT;
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException e){
            return "";  // Retorna 401 após uma requisição desse user -> Seja token expirado etc
        }

    }


    private Instant generateExpirationDate(){   // Instant -> próprio do java
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); // Expira token em 2h
    }


}
