package dto.user;

public record UserResponseDTO (
        Long idUser,
        String username,
        String email
) {
}
