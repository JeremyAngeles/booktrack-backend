package service;

import dto.log.ReadingLogRequestDTO;
import dto.log.ReadingLogResponseDTO;
import dto.log.ReadingLogUpdateDTO;
import exceptions.RecursoNoEncontrado;
import exceptions.ValidacionDeIdentidad;
import lombok.RequiredArgsConstructor;
import model.Book;
import model.ReadingLog;
import model.User;
import org.springframework.stereotype.Service;
import repository.BookRepository;
import repository.ReadingLogRepository;
import repository.UserRepository;
import service.interfaz.IReadingLogService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingLogService implements IReadingLogService {

    private final ReadingLogRepository readingLogRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public ReadingLogResponseDTO crearRegistroLibro(Long userId, ReadingLogRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ValidacionDeIdentidad("Usuario no encontrado"));

        Book book = bookRepository.findById(dto.bookId())
                .orElseThrow(() -> new ValidacionDeIdentidad("Libro no encontrado"));

        if (readingLogRepository.existsByUserIdAndBookId(user.getIdUser(),book.getIdBook()))
            throw new ValidacionDeIdentidad("Ya tienes este libro en tu historial");

        ReadingLog log = mapearAEntidad(dto);
        log.setUser(user);
        log.setBook(book);
        return mapearADto(readingLogRepository.save(log));
    }

    @Override
    public ReadingLogResponseDTO actualizarEstadoLibro(Long idReading, ReadingLogUpdateDTO dto) {
        ReadingLog readingLog = readingLogRepository.findById(idReading)
                .orElseThrow(() -> new ValidacionDeIdentidad("El registro no se encontro"));
        readingLog.setStatus(dto.status());
        readingLog.setRating(dto.rating());
        readingLog.setReview(dto.review());
        readingLog.setFavoriteParts(dto.favoriteParts());
        if ("TERMINADO".equalsIgnoreCase(dto.status())) {
            readingLog.setFinishDate(LocalDateTime.now());
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
        return readingLogRepository.findByUserIdAndBookTitleContainingIgnoreCase(user.getIdUser(), title)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public List<ReadingLogResponseDTO> buscarPorAutor(Long userId, String autor) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ValidacionDeIdentidad("Usuario no encontrado"));
        if (autor == null || autor.trim().isEmpty()) return new ArrayList<>();
        return readingLogRepository.findByUserIdAndBookAuthorsContainingIgnoreCase(user.getIdUser(),autor)
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

    private ReadingLogResponseDTO mapearADto(ReadingLog log){
        return new ReadingLogResponseDTO(
                log.getIdReading(),
                log.getStatus(),
                log.getRating(),
                log.getReview(),
                log.getFavoriteParts(),
                log.getStartDate(),
                log.getFinishDate(),
                log.getBook().getTitle(),
                log.getBook().getImageUrl(),
                log.getUser().getUsername()
        );
    }
}
