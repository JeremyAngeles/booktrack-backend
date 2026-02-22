package repository;

import model.Book;
import model.ReadingLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadingLogRepository extends JpaRepository<ReadingLog,Long> {
    boolean existsByUserIdAndBookId(Long userId , Long bookId);
    List<ReadingLog>findByUserId(Long userId);
    List<ReadingLog>findByUserIdAndBookTitleContainingIgnoreCase(Long userId,String title);
    List<ReadingLog>findByUserIdAndBookAuthorsContainingIgnoreCase(Long userId, String autor);
}
