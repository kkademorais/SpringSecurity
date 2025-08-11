package com.SpringSecurityDemo.SpringSecurity.domain.user;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

public record UserRequestDTO(
        @UniqueElements
        @NotNull
        String login,

        @NotNull
        String password,

        @NotNull
        UserRole role
) {}
