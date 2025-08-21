package com.SpringSecurityDemo.SpringSecurity.services;

import com.SpringSecurityDemo.SpringSecurity.domain.user.User;
import com.SpringSecurityDemo.SpringSecurity.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository){
        this.userRepository = userRepository; // Injeção de Dependência via construtor
    }

    public UserRepository getUserRepository() {return userRepository;}

        //POST: Salva novo user no BD -> /register
    public void addUser(User user){this.userRepository.save(user);}


    //Implementa serviço de busca do usuário
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);    //Busca do usuário dentro do Banco de Dados
    }
}
