package service.interfaz;

import dto.log.ReadingLogRequestDTO;
import dto.log.ReadingLogResponseDTO;
import dto.log.ReadingLogUpdateDTO;
import model.ReadingLog;

import java.util.List;

public interface IReadingLogService {

    ReadingLogResponseDTO crearRegistroLibro(Long userId,ReadingLogRequestDTO readingLogRequestDTO);
    ReadingLogResponseDTO actualizarEstadoLibro(Long idReading, ReadingLogUpdateDTO readingLogUpdateDTO );
    String eliminarLibro(Long idReading);

    List<ReadingLogResponseDTO>listarHistorialLibros(Long userId);
    List<ReadingLogResponseDTO>buscarPorTitulo(Long userId,String title);
    List<ReadingLogResponseDTO>buscarPorAutor(Long userId, String autor);
}
