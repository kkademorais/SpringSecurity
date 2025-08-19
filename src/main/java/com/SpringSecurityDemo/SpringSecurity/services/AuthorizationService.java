package com.SpringSecurityDemo.SpringSecurity.services;

import com.SpringSecurityDemo.SpringSecurity.repositories.UserRepository;
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


    //Implementa serviço de busca do usuário
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);    //Busca do usuário dentro do Banco de Dados
    }
}
