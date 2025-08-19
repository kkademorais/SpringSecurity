package com.SpringSecurityDemo.SpringSecurity.repositories;

import com.SpringSecurityDemo.SpringSecurity.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

//T1 => Entidade -> Classe user
    //T2 => Tipo primário -> ID (String)
public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);  //Usado futuramente pelo SpringSecurity
}
