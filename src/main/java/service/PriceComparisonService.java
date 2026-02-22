package service;

import dto.price.PriceComparisonRequestDTO;
import dto.price.PriceComparisonResponseDTO;
import dto.price.PriceComparisonUpdateDTO;
import exceptions.ValidacionDeIdentidad;
import lombok.RequiredArgsConstructor;
import model.Book;
import model.PriceComparison;
import model.User;
import org.springframework.stereotype.Service;
import repository.BookRepository;
import repository.PriceComparisonRepository;
import repository.UserRepository;
import service.interfaz.IPriceComparisonService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceComparisonService implements IPriceComparisonService {

    private final PriceComparisonRepository priceComparisonRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public PriceComparisonResponseDTO agregarPrecio(Long idUser, PriceComparisonRequestDTO dto) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ValidacionDeIdentidad("Usuario no encontrado"));
        Book book = bookRepository.findById(dto.bookId())
                .orElseThrow(()-> new ValidacionDeIdentidad("Libro no encontrado"));

        PriceComparison price = mapearAEntidad(dto);
        price.setBook(book);
        price.setUser(user);
        return mapearADto(priceComparisonRepository.save(price));
    }

    @Override
    public List<PriceComparisonResponseDTO> listarPrecios(Long idUser, Long idBook) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ValidacionDeIdentidad("Usuario no encontrado"));
        Book book = bookRepository.findById(idBook)
                .orElseThrow(() -> new ValidacionDeIdentidad("Libro no encontrado"));
        return priceComparisonRepository.findByUserIdAndBookId(user.getIdUser(),book.getIdBook())
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public PriceComparisonResponseDTO actualizarPrecio(Long idPrice, PriceComparisonUpdateDTO dto) {
        PriceComparison priceComparison = priceComparisonRepository.findById(idPrice)
                .orElseThrow(() -> new ValidacionDeIdentidad("Precio no encontrado"));
        priceComparison.setStoreName(dto.storeName());
        priceComparison.setPrice(dto.price());
        priceComparison.setCurrency(dto.currency());
        priceComparison.setStoreLink(dto.storeLink());
        priceComparisonRepository.save(priceComparison);
        return mapearADto(priceComparison);
    }

    @Override
    public String eliminarPrecio(Long idPrice) {
        PriceComparison priceComparison= priceComparisonRepository.findById(idPrice)
                .orElseThrow(() -> new ValidacionDeIdentidad("Precio no encontrado"));
        priceComparisonRepository.deleteById(priceComparison.getIdPrice());
        return "Precio eliminado correctamente!!";
    }

    private PriceComparison mapearAEntidad(PriceComparisonRequestDTO dto){
        PriceComparison priceComparison = new PriceComparison();
        priceComparison.setStoreName(dto.storeName());
        priceComparison.setPrice(dto.price());
        priceComparison.setCurrency(dto.currency());
        priceComparison.setStoreLink(dto.storeLink());


        priceComparison.setSeenAt(LocalDateTime.now());

        return priceComparison;
    }

    private PriceComparisonResponseDTO mapearADto(PriceComparison price){
        return new PriceComparisonResponseDTO(
                price.getIdPrice(),
                price.getStoreName(),
                price.getPrice(),
                price.getCurrency(),
                price.getStoreLink(),
                price.getSeenAt(),
                price.getUser().getUsername(),
                price.getBook().getTitle()
        );
    }
}
