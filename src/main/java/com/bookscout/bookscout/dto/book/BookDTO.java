package com.bookscout.bookscout.dto.book;

import jakarta.validation.constraints.NotBlank;

public record BookDTO (
        Long id,

        @NotBlank(message = "Necesito el ID de google para guardarlo")
        String googleId,

        String isbn,
        @NotBlank(message = "El título es obligatorio")
        String title,
        @NotBlank(message = "El autor es obligatorio")
        String authors,
        String publisher,
        String publishedDate,
        String description,
        Integer pageCount,
        String categories,
        String imageUrl,
        String language,
        String printType,
        String previewLink
){
}
