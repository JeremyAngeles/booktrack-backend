package dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDTO (
        @NotBlank (message = "Debe ingresar tu password actual")
        String oldPassword,

        @NotBlank @Size(min = 10 , max = 255)
        String newPassword
){
}
