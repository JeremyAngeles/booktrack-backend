package service.interfaz;

import dto.price.PriceComparisonRequestDTO;
import dto.price.PriceComparisonResponseDTO;
import dto.price.PriceComparisonUpdateDTO;

import java.util.List;

public interface IPriceComparisonService {
    PriceComparisonResponseDTO agregarPrecio(Long idUser, PriceComparisonRequestDTO priceComparisonRequestDTO);
    List<PriceComparisonResponseDTO>listarPrecios(Long idUser ,Long idBook);
    PriceComparisonResponseDTO actualizarPrecio( Long idPrice,PriceComparisonUpdateDTO priceComparisonUpdateDTO);
    String eliminarPrecio(Long idPrice);
}
