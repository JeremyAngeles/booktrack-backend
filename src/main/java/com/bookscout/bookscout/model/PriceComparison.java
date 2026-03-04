package com.bookscout.bookscout.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "price_comparisons")
public class PriceComparison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idPrice;

    @NotBlank(message = "El nombre de la tienda es obligatorio")
    @Column(name = "store_name", nullable = false)
    private String storeName;

    @NotNull(message = "El precio es obligatorio")
    @Column(nullable = false)
    private BigDecimal price;

    @NotBlank(message = "La moneda es obligatoria")
    @Column(nullable = false)
    private String currency;

    @Column(name = "store_link" , length = 500)
    private String storeLink;

    @Column(name = "seen_at")
    private LocalDateTime seenAt;

    @NotNull(message = "El libro es obligatorio")
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
