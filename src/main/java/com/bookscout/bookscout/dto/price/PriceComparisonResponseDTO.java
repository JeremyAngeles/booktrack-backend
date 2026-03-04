package com.bookscout.bookscout.dto.price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceComparisonResponseDTO (
        Long idPrice,
        String storeName,
        BigDecimal price,
        String currency,
        String storeLink,
        LocalDateTime seenAt,
        String username,
        String bookTitle
) {
}
