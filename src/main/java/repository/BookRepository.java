package repository;

import dto.book.BookDTO;
import model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
