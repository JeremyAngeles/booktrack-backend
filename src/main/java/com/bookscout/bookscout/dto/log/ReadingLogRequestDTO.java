package com.bookscout.bookscout.dto.log;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReadingLogRequestDTO(

        @NotNull(message = "El ID del libro es obligatorio")
        Long bookId,

        @NotBlank(message = "El status es obligatorio")
        String status,

        @Min(1) @Max(5)
        Integer rating,

        String review,

        String favoriteParts

) {
}