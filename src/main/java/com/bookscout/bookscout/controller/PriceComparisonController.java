package com.bookscout.bookscout.controller;

import com.bookscout.bookscout.dto.price.PriceComparisonRequestDTO;
import com.bookscout.bookscout.dto.price.PriceComparisonResponseDTO;
import com.bookscout.bookscout.dto.price.PriceComparisonUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bookscout.bookscout.service.PriceComparisonService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceComparisonController {
    private final PriceComparisonService priceComparisonService;

    //Crear precio
    @PostMapping("/{idUser}")
    public ResponseEntity<PriceComparisonResponseDTO>createPrice(
            @PathVariable Long idUser,
            @Valid @RequestBody PriceComparisonRequestDTO dto
            ){
        PriceComparisonResponseDTO newPrice = priceComparisonService.agregarPrecio(idUser,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPrice);
    }

    //Listar Precios
    @GetMapping("/user/{idUser}/book/{idBook}")
    public ResponseEntity<List<PriceComparisonResponseDTO>>listPrice(
            @PathVariable Long idUser,
            @PathVariable Long idBook
    ){
        return ResponseEntity.ok(priceComparisonService.listarPrecios(idUser,idBook));
    }

    //Actualizar Precio
    @PutMapping("/{idPrice}")
    public ResponseEntity<PriceComparisonResponseDTO>updatePrice(
            @PathVariable Long idPrice,
            @Valid @RequestBody PriceComparisonUpdateDTO dto
            ){
        return ResponseEntity.ok(priceComparisonService.actualizarPrecio(idPrice,dto));
    }

    //Eliminar Precio
    @DeleteMapping("/{idPrice}")
    public ResponseEntity<String>deletePrice(@PathVariable Long idPrice){
        return ResponseEntity.ok(priceComparisonService.eliminarPrecio(idPrice));
    }

}
