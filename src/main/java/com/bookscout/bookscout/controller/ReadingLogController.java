package com.bookscout.bookscout.controller;

import com.bookscout.bookscout.dto.book.BookDTO;
import com.bookscout.bookscout.dto.log.ReadingLogRequestDTO;
import com.bookscout.bookscout.dto.log.ReadingLogResponseDTO;
import com.bookscout.bookscout.dto.log.ReadingLogUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookscout.bookscout.service.ReadingLogService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/reading-logs")
@RequiredArgsConstructor
public class ReadingLogController {
    private final ReadingLogService readingLogService;



    //Crear registro
    @PostMapping("/user/{idUser}")
    public ResponseEntity<ReadingLogResponseDTO>createReadingLog(
            @PathVariable Long idUser,
            @Valid @RequestBody ReadingLogRequestDTO dto
            ){
        ReadingLogResponseDTO newReadingLog = readingLogService.crearRegistroLibro(idUser,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReadingLog);
    }

    //Actualizar registro
    @PutMapping("/{idReading}")
    public ResponseEntity<ReadingLogResponseDTO>updateReadingLog(
            @PathVariable Long idReading,
            @Valid @RequestBody ReadingLogUpdateDTO dto
            ){
        return ResponseEntity.ok(readingLogService.actualizarEstadoLibro(idReading,dto));
    }

    //Eliminar registro
    @DeleteMapping("/{idReading}")
    public ResponseEntity<String>deleteReadingLog(@PathVariable Long idReading){
        return ResponseEntity.ok(readingLogService.eliminarLibro(idReading));
    }

    //Listar Historial de Libros
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ReadingLogResponseDTO>>listReadingLog(@PathVariable Long idUser){
        return ResponseEntity.ok(readingLogService.listarHistorialLibros(idUser));
    }

    //Buscar por Titulo
    @GetMapping("/user/{idUser}/search-title")
    public ResponseEntity<List<ReadingLogResponseDTO>>searchTitle(
            @PathVariable Long idUser,
            @RequestParam String title
    ){
        return ResponseEntity.ok(readingLogService.buscarPorTitulo(idUser,title));
    }

    //Buscar por Autor
    @GetMapping("/user/{idUser}/search-author")
    public ResponseEntity<List<ReadingLogResponseDTO>>searchAuthor(
            @PathVariable Long idUser,
            @RequestParam String author
    ){
        return ResponseEntity.ok(readingLogService.buscarPorAutor(idUser,author));
    }

    @PostMapping("/save-google/{userId}")
    public ResponseEntity<String> guardarLibroDeGoogle(
            @PathVariable Long userId,
            @RequestBody BookDTO bookDTO
    ) {
        readingLogService.guardarLibroEnLog(userId, bookDTO, "TO_READ");

        return ResponseEntity.ok("¡Libro guardado en tu biblioteca correctamente!");
    }
}
