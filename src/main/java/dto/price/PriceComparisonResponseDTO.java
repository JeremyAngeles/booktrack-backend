package dto.price;

import java.time.LocalDateTime;

public record PriceComparisonResponseDTO (
        Long idPrice,
        String storeName,
        Double price,
        String currency,
        String storeLink,
        LocalDateTime seenAt,
        String username,
        String bookTitle
) {
}
