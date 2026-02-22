package dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO (
        @NotBlank(message = "El usuario es obligatorio")
        String username,
        @NotBlank(message = "El password es obligatorio")
        String password
){
}
