package dto.price;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PriceComparisonRequestDTO(
    @NotBlank String storeName,
    @NotNull Double price,
    @NotBlank String currency,
    String storeLink,
    @NotNull Long bookId
) {
}
