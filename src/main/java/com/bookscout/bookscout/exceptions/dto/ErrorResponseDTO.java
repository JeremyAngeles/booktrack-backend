package com.bookscout.bookscout.exceptions.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        String message,
        List<String> details,
        LocalDateTime timestamp
) {
}