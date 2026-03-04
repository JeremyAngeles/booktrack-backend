    package com.bookscout.bookscout.dto.user;

    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Size;

    public record UpdateUsernameDTO(
            @NotBlank(message = "El nuevo username es obligatorio, no puede estar vacio")
            @Size(min = 5 , max= 50)
            String newUsername
    ) {
    }
