package com.bookscout.bookscout.service.interfaz;

import com.bookscout.bookscout.dto.user.*;

import java.util.List;

public interface IUserService {

    UserResponseDTO registrarUsuario(UserRegistrationDTO userRegistrationDTO);

    UserResponseDTO loginUsuario(LoginRequestDTO loginRequestDTO);

    String cambiarPassword(Long userId, UpdatePasswordDTO updatePasswordDTO);

    UserResponseDTO cambiarUsuario(Long userId, UpdateUsernameDTO updateUsernameDTO);

    List<UserResponseDTO> listarUsuario();

    String solicitarRecuperacionPassword(String email);

    String completarRecuperacion(String token, String nuevaPassword);
}
