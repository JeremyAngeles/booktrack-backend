package com.bookscout.bookscout.dto.log;


import com.bookscout.bookscout.dto.book.BookDTO;

import java.time.LocalDate;

public record ReadingLogResponseDTO (
        Long idReading,
        String status,
        Integer rating,
        String review,
        String favoriteParts,
        LocalDate startDate,
        LocalDate finishDate,
        String username,
        BookDTO book
) {
}
