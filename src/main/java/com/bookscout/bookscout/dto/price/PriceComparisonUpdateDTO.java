package com.bookscout.bookscout.dto.price;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PriceComparisonUpdateDTO (
        @NotBlank(message = "La tienda no puede estar vacia")
        String storeName,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal price,

        @NotBlank(message = "La moneda es obligatoria")
        String currency,

        String storeLink
){
}
