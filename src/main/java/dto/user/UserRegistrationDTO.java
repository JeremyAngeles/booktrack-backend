package dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(

        @NotBlank @Size(min = 5, max = 50)
        String username,
        @NotBlank @Email(message = "Formato invalido")
        String email,
        @NotBlank @Size(min=10, max = 100)
        String password
) {
}
