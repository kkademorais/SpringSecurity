package com.SpringSecurityDemo.SpringSecurity.controllers;

import com.SpringSecurityDemo.SpringSecurity.domain.user.RegisterRequestDTO;
import com.SpringSecurityDemo.SpringSecurity.domain.user.User;
import com.SpringSecurityDemo.SpringSecurity.domain.user.UserRequestDTO;
import com.SpringSecurityDemo.SpringSecurity.services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    private final AuthorizationService authorizationService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthorizationService authorizationService){
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
    }


    @PostMapping("/login") // Endpoint de POST
    public ResponseEntity login(@RequestBody @Valid UserRequestDTO data){
         UsernamePasswordAuthenticationToken usernamePasswordHash = new UsernamePasswordAuthenticationToken(data.login(), data.password()); // Transforma credenciais em token que passou por Hash
         var auth = this.authenticationManager.authenticate(usernamePasswordHash); // Validação do token
         return ResponseEntity.ok().build();
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

}
