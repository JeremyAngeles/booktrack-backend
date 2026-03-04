package com.bookscout.bookscout.repository;

import com.bookscout.bookscout.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {
        //ESTO VERIFICA QUE EL ID DE GOOGLE YA EXISTE
        boolean existsByGoogleId(String googleId);
        //TRAE TODA LA TABLA DE BOOK PARA TRAER SU IDBOOK
        Optional<Book> findByGoogleId(String googleId);

         List<Book> findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(
            String title, String authors
            );

        List<Book>findByCategoriesContainingIgnoreCase(String categories);

        List<Book>findByLanguageContainingIgnoreCase(String language);


}
