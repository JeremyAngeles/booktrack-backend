package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String review;

    @Column(name = "favorite_parts" , columnDefinition = "NVARCHAR(MAX)")
    private String favoriteParts;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "finish_date")
    private LocalDateTime finishDate;

    @NotNull(message = "El libro es obligatorio")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
