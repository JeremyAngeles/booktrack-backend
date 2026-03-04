package com.bookscout.bookscout.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3,max = 50)
    @Column(nullable = false,unique = true,length = 50)
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe tener un formato email tradicional @")
    @Column(nullable = false,unique = true,length = 100)
    private String email;

    @NotBlank(message = "El password es obligatorio")
    @Size(min = 10 , max = 100)
    @Column(nullable = false)
    private String password;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @NotNull
    @Column(nullable = false,length = 20)
    private String role = "USER";


    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void asignarFechaCreacion() {
        this.createdAt = LocalDateTime.now();
    }
}
