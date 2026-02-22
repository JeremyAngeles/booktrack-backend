package service;

import dto.book.BookDTO;
import lombok.RequiredArgsConstructor;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BookRepository;
import service.interfaz.IBookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository  bookRepository;

        @Override
        public BookDTO agregarLibro(BookDTO bookDTO) {

            if (bookRepository.existsByGoogleId(bookDTO.googleId())){
            return buscarLibroPorIdGoogle(bookDTO.googleId())
                    .orElseThrow(() -> new RuntimeException("Error al recuperar el libro existente"));
            }

            Book nuevoLibro = mapearAEntidad(bookDTO);
            Book libroGuardado = bookRepository.save(nuevoLibro);
            return mapearADto(libroGuardado);
        }

    @Override
    public List<BookDTO> listarLibros() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public List<BookDTO> buscadorPorTituloOAutor(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) return new ArrayList<>();
        String texto = filtro.trim();
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(texto, texto)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public List<BookDTO> buscadorPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) return new ArrayList<>();
        return bookRepository.findByCategoriesContainingIgnoreCase(categoria)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public List<BookDTO> buscadorPorLenguage(String lenguage) {
        if (lenguage == null || lenguage.trim().isEmpty()) return new ArrayList<>();
        return bookRepository.findByLanguageContainingIgnoreCase(lenguage)
                .stream()
                .map(this::mapearADto)
                .toList();
    }


    @Override
    public Optional<BookDTO> buscarLibroPorIdGoogle(String googleId) {

        return bookRepository.findByGoogleId(googleId)
                .map(this::mapearADto);
    }

    //Guardar algo debo convertirlo a entity
    private Book mapearAEntidad(BookDTO bookDTO){
        Book book = new Book();
        book.setIdGoogle(bookDTO.googleId());
        book.setIsbn(bookDTO.isbn());
        book.setTitle(bookDTO.title());
        book.setAuthors(bookDTO.authors());
        book.setPublisher(bookDTO.publisher());
        book.setPublishedDate(bookDTO.publishedDate());
        book.setDescription(bookDTO.description());
        book.setPageCount(bookDTO.pageCount());
        book.setCategories(bookDTO.categories());
        book.setImageUrl(bookDTO.imageUrl());
        book.setLanguage(bookDTO.language());
        book.setPrintType(bookDTO.printType());
        book.setPreviewLink(bookDTO.previewLink());
        return book;
    }
    //Cuando devuelvo algo de la bd devo enviarlo como dto
    private BookDTO mapearADto(Book book){
        return new BookDTO(
                book.getIdBook(),
                book.getIdGoogle(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthors(),
                book.getPublisher(),
                book.getPublishedDate(),
                book.getDescription(),
                book.getPageCount(),
                book.getCategories(),
                book.getImageUrl(),
                book.getLanguage(),
                book.getPrintType(),
                book.getPreviewLink()
        );
    }
}
