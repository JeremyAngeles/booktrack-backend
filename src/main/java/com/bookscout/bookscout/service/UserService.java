package com.bookscout.bookscout.service;
import com.bookscout.bookscout.dto.user.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.bookscout.bookscout.exceptions.ValidacionDeIdentidad;
import lombok.RequiredArgsConstructor;
import com.bookscout.bookscout.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bookscout.bookscout.repository.UserRepository;
import com.bookscout.bookscout.service.interfaz.IUserService;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Override
    public UserResponseDTO registrarUsuario(UserRegistrationDTO userRegistrationDTO) {
        if (userRepository.existsByUsername(userRegistrationDTO.username())) {
            throw new ValidacionDeIdentidad("El nombre de usuario ya está en uso");
        }

        if (userRepository.existsByEmail(userRegistrationDTO.email())) {
            throw new ValidacionDeIdentidad("El email ya se encuentra en uso");
        }

        User nuevoUsuario = new User();
        nuevoUsuario.setUsername(userRegistrationDTO.username());
        nuevoUsuario.setEmail(userRegistrationDTO.email());

        nuevoUsuario.setPassword( passwordEncoder.encode(userRegistrationDTO.password()));

        User usuarioGuardado = userRepository.save(nuevoUsuario);

        return mapearADto(usuarioGuardado);
    }

    @Override
    public UserResponseDTO loginUsuario(LoginRequestDTO loginRequestDTO) {
        User usuario = userRepository.findByUsername(loginRequestDTO.username())
                .orElseThrow(() -> new ValidacionDeIdentidad("El usuario o la contraseña son incorrectos"));

        boolean coinciden = passwordEncoder.matches(loginRequestDTO.password() , usuario.getPassword());

        if (!coinciden){
            throw new ValidacionDeIdentidad("El usuario y la contraseña son incorrectas");
        }

        return mapearADto(usuario);
    }

    @Override
    public String cambiarPassword(Long userId, UpdatePasswordDTO updatePasswordDTO) {
        User usuario = userRepository.findById(userId).orElseThrow(()->
                new ValidacionDeIdentidad("El usuario no existe"));
        if (passwordEncoder.matches(updatePasswordDTO.newPassword(), usuario.getPassword())) {
            throw new ValidacionDeIdentidad("La nueva contraseña no puede ser igual a la anterior");
        }
        boolean coinciden = passwordEncoder.matches(updatePasswordDTO.oldPassword() , usuario.getPassword());
        if (!coinciden)
            throw  new ValidacionDeIdentidad("El password no coincide");


        usuario.setPassword(passwordEncoder.encode(updatePasswordDTO.newPassword()));
        userRepository.save(usuario);
        return "La contraseña ha sido cambiada!";
    }

    @Override
    public UserResponseDTO cambiarUsuario(Long userId, UpdateUsernameDTO updateUsernameDTO) {
        User usuario = userRepository.findById(userId).orElseThrow(()->
                new ValidacionDeIdentidad("El usuario no existe"));
        if (Objects.equals(updateUsernameDTO.newUsername(), usuario.getUsername())){
            throw new ValidacionDeIdentidad("El nuevo nombre de usuario no puede ser igual al actual");
        }
        if (userRepository.existsByUsername(updateUsernameDTO.newUsername())){
            throw new ValidacionDeIdentidad("El nombre de usuario ya existe");
        }
        usuario.setUsername(updateUsernameDTO.newUsername());
        this.userRepository.save(usuario);
        return mapearADto(usuario);
    }

    @Override
    public List<UserResponseDTO> listarUsuario() {
        return userRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public String solicitarRecuperacionPassword(String email) {

        User usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new ValidacionDeIdentidad("No existe un usuario con ese correo"));


        String token = UUID.randomUUID().toString();


        usuario.setResetPasswordToken(token);
        userRepository.save(usuario);

        enviarEmailRecuperacion(usuario, token);
        return "Se ha enviado un enlace de recuperación a su correo electrónico.";
    }

    @Override
    public String completarRecuperacion(String token, String nuevaPassword) {

        User usuario = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new ValidacionDeIdentidad("El código de recuperación es inválido o expiró"));


        usuario.setPassword(passwordEncoder.encode(nuevaPassword));


        usuario.setResetPasswordToken(null);

        userRepository.save(usuario);

        return "Tu contraseña ha sido restablecida con éxito";
    }

    private void enviarEmailRecuperacion(User usuario, String token) {
        String linkRecuperacion = "http://localhost:5173/reset-password?token=" + token;

        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setFrom("elmapachelector076@gmail.com");

        mensaje.setTo(usuario.getEmail());
        mensaje.setSubject("Recuperación de Contraseña - BookScout");
        mensaje.setText("Hola " + usuario.getUsername() + ",\n\n" +
                "Has solicitado restablecer tu contraseña. Haz clic en el siguiente enlace para continuar:\n" +
                linkRecuperacion + "\n\n" +
                "Si no solicitaste esto, puedes ignorar este correo.");

        mailSender.send(mensaje);
    }

    private User mapearAEntidad(UserResponseDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        return user;
    }

    // Convertir de Entidad a DTO (Para ENVIAR a React)
    private UserResponseDTO mapearADto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
