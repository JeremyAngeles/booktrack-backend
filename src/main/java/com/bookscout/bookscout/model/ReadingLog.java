package com.bookscout.bookscout.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;


@Entity
@Data
@Table(name = "reading_logs")
public class ReadingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idReading;

    @NotBlank(message = "El status es obligatorio")
    @Column(nullable = false)
    private String status;

    @Min(1) @Max(5)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String review;

    @Column(columnDefinition = "TEXT" ,name = "favorite_parts")
    private String favoriteParts;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @NotNull(message = "El libro es obligatorio")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
