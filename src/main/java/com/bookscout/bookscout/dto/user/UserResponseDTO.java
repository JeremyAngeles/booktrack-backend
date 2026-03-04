package com.bookscout.bookscout.dto.user;

public record UserResponseDTO (
        Long id,
        String username,
        String email
) {
}
