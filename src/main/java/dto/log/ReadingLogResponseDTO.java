package dto.log;

import java.time.LocalDateTime;

public record ReadingLogResponseDTO (
        Long idReading,
        String status,
        Integer rating,
        String review,
        String favoriteParts,
        LocalDateTime startDate,
        LocalDateTime finishDate,
        String bookTitle,
        String bookImageUrl,
        String username
) {
}
