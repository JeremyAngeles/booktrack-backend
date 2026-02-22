package service.interfaz;

import dto.book.BookDTO;
import model.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    BookDTO agregarLibro(BookDTO bookDTO);

    List<BookDTO>listarLibros();

    List<BookDTO>buscadorPorTituloOAutor(String filtro);

    List<BookDTO>buscadorPorCategoria(String categoria);

    List<BookDTO>buscadorPorLenguage(String lenguage);

    Optional<BookDTO> buscarLibroPorIdGoogle(String googleId);
}
