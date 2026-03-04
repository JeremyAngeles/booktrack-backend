package com.bookscout.bookscout.dto.log;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ReadingLogUpdateDTO (
        @NotBlank(message = "El status es obligatorio")
        String status,

        @Min(1) @Max(5)
        Integer rating,

        String review,

        String favoriteParts,

        LocalDate startDate,
        LocalDate finishDate
) {
}
