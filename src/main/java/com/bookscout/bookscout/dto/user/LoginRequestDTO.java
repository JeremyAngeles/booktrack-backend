package com.bookscout.bookscout.dto.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
        @NotBlank(message = "El usuario es obligatorio")
        String username,
        @NotBlank(message = "El password es obligatorio")
        String password
){
}
