package com.bookscout.bookscout.model;

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
    private Long id;

    @Column(name = "google_id",unique = true,nullable = false,length = 100)
    private String googleId;

    @Column(length = 50)
    private String isbn;

    @Column(nullable = false,length = 500)
    private String title;

    @Column(length = 500)
    private String authors;

    @Column(length = 255)
    private String publisher;

    @Column(name = "published_date")
    private String publishedDate;

    @Column(columnDefinition = "VARCHAR(MAX)")
    private String description;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(length = 255)
    private String categories;

    @Column(name = "image_url", length = 3000)
    private String imageUrl;

    private String language;

    @Column(name = "print_type")
    private String printType;

    @Column(name = "preview_link", length = 2000)
    private String previewLink;
}
