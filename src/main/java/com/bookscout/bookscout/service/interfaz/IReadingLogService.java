package com.bookscout.bookscout.service.interfaz;

import com.bookscout.bookscout.dto.book.BookDTO;
import com.bookscout.bookscout.dto.log.ReadingLogRequestDTO;
import com.bookscout.bookscout.dto.log.ReadingLogResponseDTO;
import com.bookscout.bookscout.dto.log.ReadingLogUpdateDTO;

import java.util.List;

public interface IReadingLogService {

    ReadingLogResponseDTO crearRegistroLibro(Long userId,ReadingLogRequestDTO readingLogRequestDTO);
    ReadingLogResponseDTO actualizarEstadoLibro(Long idReading, ReadingLogUpdateDTO readingLogUpdateDTO );
    String eliminarLibro(Long idReading);
    void guardarLibroEnLog(Long userId, BookDTO bookDTO, String status);
    List<ReadingLogResponseDTO>listarHistorialLibros(Long userId);
    List<ReadingLogResponseDTO>buscarPorTitulo(Long userId,String title);
    List<ReadingLogResponseDTO>buscarPorAutor(Long userId, String autor);
}
