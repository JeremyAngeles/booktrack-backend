package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idBook;

    @Column(name = "google_id",unique = true,nullable = false)
    private String idGoogle;

    private String isbn;

    @Column(nullable = false)
    private String title;

    private String authors;

    private String publisher;

    @Column(name = "published_date")
    private String publishedDate;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "page_count")
    private Integer pageCount;

    private String categories;

    @Column(name = "image_url" , length = 500)
    private String imageUrl;

    private String language;

    @Column(name = "print_type", length = 500)
    private String printType;

    @Column(name = "preview_link", length = 500)
    private String previewLink;
}
