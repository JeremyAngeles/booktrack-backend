package com.bookscout.bookscout.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordDTO(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Debe ser un formato de email válido")
        String email
) {
}