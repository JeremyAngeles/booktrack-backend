package com.bookscout.bookscout.controller;

import com.bookscout.bookscout.dto.user.*;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookscout.bookscout.service.UserService;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //Registrar usuario
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO>createUser(@Valid @RequestBody UserRegistrationDTO dto){
        UserResponseDTO newUser = userService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    //Login usuario
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO>loginUser(@Valid @RequestBody LoginRequestDTO dto){
        return ResponseEntity.ok(userService.loginUsuario(dto));
    }

    //Cambiar Contraseña
    @PutMapping("/user/{idUser}/update-password")
    public ResponseEntity<String>updatePassword(
            @PathVariable Long idUser,
            @Valid @RequestBody UpdatePasswordDTO dto
            ){
        return ResponseEntity.ok(userService.cambiarPassword(idUser,dto));
    }

    //Cambiar nombre de Usuario
    @PutMapping("/user/{idUser}/update-username")
    public ResponseEntity<UserResponseDTO>updateUsername(
            @PathVariable Long idUser,
            @Valid @RequestBody UpdateUsernameDTO dto
            ){
        return ResponseEntity.ok(userService.cambiarUsuario(idUser,dto));
    }

    //Solicitar Recuperacion de password
    @PostMapping("/forgot-password")
    public ResponseEntity<String>requestRecovery(@Valid @RequestBody ForgotPasswordDTO dto){
        userService.solicitarRecuperacionPassword(dto.email());
        return ResponseEntity.ok("Se ha enviado un enlace de recuperación a tu correo.");
    }

    //Completar Recuperacion de password
    @PostMapping("/reset-password")
    public ResponseEntity<String>completeRecovery(@Valid @RequestBody ResetPasswordDTO dto){
        userService.completarRecuperacion(dto.token(), dto.newPassword());
        return ResponseEntity.ok("Contraseña restablecida con éxito.");
    }
}
