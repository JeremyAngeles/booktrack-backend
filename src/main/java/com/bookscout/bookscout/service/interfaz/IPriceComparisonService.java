package com.bookscout.bookscout.service.interfaz;

import com.bookscout.bookscout.dto.price.PriceComparisonRequestDTO;
import com.bookscout.bookscout.dto.price.PriceComparisonResponseDTO;
import com.bookscout.bookscout.dto.price.PriceComparisonUpdateDTO;

import java.util.List;

public interface IPriceComparisonService {
    PriceComparisonResponseDTO agregarPrecio(Long idUser, PriceComparisonRequestDTO priceComparisonRequestDTO);
    List<PriceComparisonResponseDTO>listarPrecios(Long idUser ,Long idBook);
    PriceComparisonResponseDTO actualizarPrecio( Long idPrice,PriceComparisonUpdateDTO priceComparisonUpdateDTO);
    String eliminarPrecio(Long idPrice);
}
