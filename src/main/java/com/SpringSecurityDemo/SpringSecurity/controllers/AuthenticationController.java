package com.SpringSecurityDemo.SpringSecurity.controllers;

import com.SpringSecurityDemo.SpringSecurity.domain.user.LoginResponseDTO;
import com.SpringSecurityDemo.SpringSecurity.domain.user.RegisterRequestDTO;
import com.SpringSecurityDemo.SpringSecurity.domain.user.User;
import com.SpringSecurityDemo.SpringSecurity.domain.user.UserRequestDTO;
import com.SpringSecurityDemo.SpringSecurity.infra.security.TokenService;
import com.SpringSecurityDemo.SpringSecurity.services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final AuthorizationService authorizationService;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthorizationService authorizationService, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenService = tokenService;
    }


    @PostMapping("/login") // Endpoint de POST
    public ResponseEntity login(@RequestBody @Valid UserRequestDTO data){
        try{
            UsernamePasswordAuthenticationToken usernamePasswordHash = new UsernamePasswordAuthenticationToken(data.login(), data.password()); // Transforma credenciais em token que passou por Hash
            var auth = this.authenticationManager.authenticate(usernamePasswordHash); // Validação do token

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));  // User recebe seu token
        }
        catch (AuthenticationException e){
            e.getAuthenticationRequest();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credencial inválida!");
        }

    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO data){
        if(authorizationService.getUserRepository().findByLogin(data.login()) != null){return ResponseEntity.badRequest().build();}

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.login(), encryptedPassword, data.userRole());

        //Salvar no Repository por meio do Service
        this.authorizationService.addUser(newUser);

        return ResponseEntity.ok().build();
    }

    //GET do usuário pelo login

}
