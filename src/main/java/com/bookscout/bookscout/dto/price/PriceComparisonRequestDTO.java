package com.bookscout.bookscout.dto.price;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PriceComparisonRequestDTO(
    @NotBlank String storeName,
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @NotNull BigDecimal price,
    @NotBlank String currency,
    String storeLink,
    @NotNull Long bookId
) {
}
