package com.SpringSecurityDemo.SpringSecurity.domain.user;

public record RegisterRequestDTO(
        String login,
        String password,
        UserRole userRole
) {}
