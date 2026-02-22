package dto.price;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PriceComparisonUpdateDTO (
        @NotBlank(message = "La tienda no puede estar vacia")
        String storeName,

        @NotNull(message = "El precio es obligatorio")
        Double price,

        @NotBlank(message = "La moneda es obligatoria")
        String currency,

        String storeLink
){
}
