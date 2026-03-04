package com.bookscout.bookscout.service;

import com.bookscout.bookscout.dto.book.BookDTO;
import com.bookscout.bookscout.dto.log.ReadingLogRequestDTO;
import com.bookscout.bookscout.dto.log.ReadingLogResponseDTO;
import com.bookscout.bookscout.dto.log.ReadingLogUpdateDTO;
import com.bookscout.bookscout.exceptions.ValidacionDeIdentidad;
import com.bookscout.bookscout.service.interfaz.IBookService;
import lombok.RequiredArgsConstructor;
import com.bookscout.bookscout.model.Book;
import com.bookscout.bookscout.model.ReadingLog;
import com.bookscout.bookscout.model.User;
import org.springframework.stereotype.Service;
import com.bookscout.bookscout.repository.BookRepository;
import com.bookscout.bookscout.repository.ReadingLogRepository;
import com.bookscout.bookscout.repository.UserRepository;
import com.bookscout.bookscout.service.interfaz.IReadingLogService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingLogService implements IReadingLogService {

    private final ReadingLogRepository readingLogRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final IBookService bookService;

    public void guardarLibroEnLog(Long userId, BookDTO bookDTO, String status) {
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Tu método agregarLibro ahora devuelve el libro (existente o nuevo) sin error
        BookDTO libroGuardadoDTO = bookService.agregarLibro(bookDTO);

        Book libroEntidad = bookRepository.findByGoogleId(libroGuardadoDTO.googleId())
                .orElseThrow(() -> new RuntimeException("Error crítico: El libro debería existir"));

        boolean yaLoTiene = readingLogRepository.existsByUserAndBook(usuario, libroEntidad);
        if (yaLoTiene) {
            throw new RuntimeException("Ya tienes este libro en tu biblioteca");
        }

        ReadingLog log = new ReadingLog();
        log.setUser(usuario);
        log.setBook(libroEntidad);
        log.setStatus(status != null ? status : "TO_READ");
        log.setStartDate(LocalDate.now());


        readingLogRepository.save(log);
    }

    @Override
    public ReadingLogResponseDTO crearRegistroLibro(Long userId, ReadingLogRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ValidacionDeIdentidad("Usuario no encontrado"));

        Book book = bookRepository.findById(dto.bookId())
                .orElseThrow(() -> new ValidacionDeIdentidad("Libro no encontrado"));

        if (readingLogRepository.existsByUserIdAndBookId(user.getId(),book.getId()))
            throw new ValidacionDeIdentidad("Ya tienes este libro en tu historial");

        ReadingLog log = mapearAEntidad(dto);
        log.setUser(user);
        log.setBook(book);
        return mapearADto(readingLogRepository.save(log));
    }

    @Override
    public ReadingLogResponseDTO actualizarEstadoLibro(Long idReading, ReadingLogUpdateDTO dto) {
        ReadingLog readingLog = readingLogRepository.findById(idReading)
                .orElseThrow(() -> new ValidacionDeIdentidad("El registro no se encontró"));

        readingLog.setStatus(dto.status());
        readingLog.setRating(dto.rating());
        readingLog.setReview(dto.review());
        readingLog.setFavoriteParts(dto.favoriteParts());

        if ("TO_READ".equals(dto.status())) {
            readingLog.setStartDate(null);
            readingLog.setFinishDate(null);
        }

        else {
            readingLog.setStartDate(dto.startDate());
            readingLog.setFinishDate(dto.finishDate());
        }

        if ("FINISHED".equalsIgnoreCase(dto.status()) && readingLog.getFinishDate() == null) {
            readingLog.setFinishDate(LocalDate.now());
        }
        if ("READING".equalsIgnoreCase(dto.status()) && readingLog.getStartDate() == null) {
            readingLog.setStartDate(LocalDate.now());
        }

        ReadingLog logActualizado = readingLogRepository.save(readingLog);
        return mapearADto(logActualizado);
    }

    @Override
    public String eliminarLibro(Long idReading) {
        readingLogRepository.findById(idReading)
                .orElseThrow(() -> new ValidacionDeIdentidad("Registro no encontrado"));

        readingLogRepository.deleteById(idReading);
        return "El registro se eliminó correctamente";
    }

    @Override
    public List<ReadingLogResponseDTO> listarHistorialLibros(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ValidacionDeIdentidad("Usuario no encontrado"));
        return readingLogRepository.findByUserId(userId)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public List<ReadingLogResponseDTO> buscarPorTitulo(Long userId, String title) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ValidacionDeIdentidad("Usuario no encontrado"));
        if (title == null || title.trim().isEmpty()) return new ArrayList<>();
        return readingLogRepository.findByUserIdAndBookTitleContainingIgnoreCase(user.getId(), title)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public List<ReadingLogResponseDTO> buscarPorAutor(Long userId, String autor) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ValidacionDeIdentidad("Usuario no encontrado"));
        if (autor == null || autor.trim().isEmpty()) return new ArrayList<>();
        return readingLogRepository.findByUserIdAndBookAuthorsContainingIgnoreCase(user.getId(),autor)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    private ReadingLog mapearAEntidad(ReadingLogRequestDTO dto){
        ReadingLog readingLog = new ReadingLog();
        readingLog.setStatus(dto.status());
        readingLog.setRating(dto.rating());
        readingLog.setReview(dto.review());
        readingLog.setFavoriteParts(dto.favoriteParts());

        return readingLog;
    }

    private ReadingLogResponseDTO mapearADto(ReadingLog log) {
        Book bookEntity = log.getBook();
        BookDTO bookDTO = new BookDTO(
                bookEntity.getId(),
                bookEntity.getGoogleId(),
                bookEntity.getIsbn(),
                bookEntity.getTitle(),
                bookEntity.getAuthors(),
                bookEntity.getPublisher(),
                bookEntity.getPublishedDate(),
                bookEntity.getDescription(),
                bookEntity.getPageCount(),
                bookEntity.getCategories(),
                bookEntity.getImageUrl(),
                bookEntity.getLanguage(),
                bookEntity.getPrintType(),
                bookEntity.getPreviewLink()
        );
        return new ReadingLogResponseDTO(
                log.getIdReading(),
                log.getStatus(),
                log.getRating(),
                log.getReview(),
                log.getFavoriteParts(),
                log.getStartDate(),
                log.getFinishDate(),
                log.getUser().getUsername(),
                bookDTO
        );
    }
}
