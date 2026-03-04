package com.bookscout.bookscout.service.interfaz;

import com.bookscout.bookscout.dto.book.BookDTO;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    BookDTO agregarLibro(BookDTO bookDTO);

    List<BookDTO>listarLibros();

    List<BookDTO>buscadorPorTituloOAutor(String filtro);

    List<BookDTO>buscadorPorCategoria(String categoria);

    List<BookDTO>buscadorPorLenguage(String lenguage);

    Optional<BookDTO> buscarLibroPorIdGoogle(String googleId);


    List<BookDTO> buscarEnGoogleBooks(String filtro, int pagina,String lang);
}
