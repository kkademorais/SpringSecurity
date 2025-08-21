package com.SpringSecurityDemo.SpringSecurity.domain.user;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

public record UserRequestDTO(
        String login,
        String password
) {}
