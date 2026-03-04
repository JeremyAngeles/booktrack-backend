package com.bookscout.bookscout.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(
        @NotBlank(message = "El token es obligatorio")
        String token,

        @NotBlank(message = "La nueva contraseña es obligatoria")
        @Size(min = 10, max = 100, message = "La contraseña debe tener entre 10 y 100 caracteres")
        String newPassword
) {
}