package com.bookscout.bookscout.repository;

import com.bookscout.bookscout.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    //Trae los datos con el username
    Optional<User> findByUsername(String username);
    //Trae los datos del email
    Optional<User> findByEmail(String email);
    // Esto permite encontrar al usuario cuando regrese desde el link del correo
    Optional<User> findByResetPasswordToken(String token);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


}
