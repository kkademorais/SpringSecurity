package com.SpringSecurityDemo.SpringSecurity.infra.security;

import com.SpringSecurityDemo.SpringSecurity.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

    // Filtro pra validação dos tokens antes de cada requisição do user
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository){
        this.tokenService = tokenService;
        this.userRepository = userRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if(token != null){  // Processamento do token
            var login = tokenService.verifyToken(token);
            UserDetails user = userRepository.findByLogin(login);   // Caso token validado sem exception -> retorna o user

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());    // Verifica credenciais
            SecurityContextHolder.getContext().setAuthentication(authentication);   // Salvou user nesse contexto em andamento
        }

        filterChain.doFilter(request, response);    // Parte pro próximo processo do pipeline -> Verificar SecurityConfigurations
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");    // Busca header da autenticação
        if(authHeader == null){return null;}
        return authHeader.replace("Bearer", "").trim();    // Tira padronização pra pegar só o token

    }

}
