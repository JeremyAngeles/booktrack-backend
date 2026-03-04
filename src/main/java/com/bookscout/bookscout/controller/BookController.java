package com.bookscout.bookscout.controller;

import com.bookscout.bookscout.dto.book.BookDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookscout.bookscout.service.BookService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    //Crear Libro
    @PostMapping
    public ResponseEntity<BookDTO>createBook(@Valid @RequestBody BookDTO book){
        BookDTO newBook = bookService.agregarLibro(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @PostMapping("/save-google")
    public ResponseEntity<BookDTO> saveGoogleBook(@RequestBody BookDTO book){
        // Usamos la misma lógica de agregar, que ya verifica duplicados
        BookDTO savedBook = bookService.agregarLibro(book);
        return ResponseEntity.ok(savedBook);
    }

    //Listar Libros
    @GetMapping
    public ResponseEntity<List<BookDTO>>listBook(){
        return ResponseEntity.ok(bookService.listarLibros());
    }


    //Buscar Libros por Titulo o Autor
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>>advancedSearch(@RequestParam String filter){
        return ResponseEntity.ok(bookService.buscadorPorTituloOAutor(filter));
    }

    //Buscar Libros por Categoria
    @GetMapping("/category")
    public ResponseEntity<List<BookDTO>>searchCategory(@RequestParam String category){
        return ResponseEntity.ok(bookService.buscadorPorCategoria(category));
    }

    //Buscar Libros por idioma
    @GetMapping("/language")
    public ResponseEntity<List<BookDTO>>searchLanguage(@RequestParam String language){
        return ResponseEntity.ok(bookService.buscadorPorLenguage(language));
    }

    //Buscar Libro de Google
    @GetMapping("/google/{googleId}")
    public ResponseEntity<BookDTO>searchBookGoogle(@PathVariable String googleId){
        return bookService.buscarLibroPorIdGoogle(googleId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //Traer todos los libros de google
    @GetMapping("/explore")
    public ResponseEntity<List<BookDTO>> explore(
            @RequestParam String filter,
            @RequestParam(defaultValue = "0") int page ,// <--- ESTO ES NUEVO
            @RequestParam(defaultValue = "es") String lang
    ) {
        return ResponseEntity.ok(bookService.buscarEnGoogleBooks(filter, page,lang));
    }


}
