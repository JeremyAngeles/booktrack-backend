package com.bookscout.bookscout.repository;

import com.bookscout.bookscout.model.Book;
import com.bookscout.bookscout.model.ReadingLog;
import com.bookscout.bookscout.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingLogRepository extends JpaRepository<ReadingLog,Long> {
    boolean existsByUserIdAndBookId(Long userId , Long bookId);
    boolean existsByUserAndBook(User user, Book book);
    List<ReadingLog>findByUserId(Long userId);
    List<ReadingLog>findByUserIdAndBookTitleContainingIgnoreCase(Long userId,String title);
    List<ReadingLog>findByUserIdAndBookAuthorsContainingIgnoreCase(Long userId, String autor);
}
