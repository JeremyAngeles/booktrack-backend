package repository;

import model.PriceComparison;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceComparisonRepository extends JpaRepository<PriceComparison,Long> {
    List<PriceComparison>findByUserIdAndBookId(Long userId ,Long bookId );
}
