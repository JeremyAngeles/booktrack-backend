package com.bookscout.bookscout.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookscout.bookscout.dto.book.BookDTO;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import com.bookscout.bookscout.model.Book;
import org.springframework.stereotype.Service;
import com.bookscout.bookscout.repository.BookRepository;
import com.bookscout.bookscout.service.interfaz.IBookService;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final RestTemplate restTemplate;
    private final BookRepository  bookRepository;

        @Override
        public BookDTO agregarLibro(BookDTO bookDTO) {
            Optional<BookDTO> libroExistente = buscarLibroPorIdGoogle(bookDTO.googleId());
            if (libroExistente.isPresent()) {
                return libroExistente.get();
            }
            Book nuevoLibro = mapearAEntidad(bookDTO);
            return mapearADto(bookRepository.save(nuevoLibro));
        }

    @Override
    public List<BookDTO> listarLibros() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public List<BookDTO> buscadorPorTituloOAutor(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) return new ArrayList<>();
        String texto = filtro.trim();
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(texto, texto)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public List<BookDTO> buscadorPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) return new ArrayList<>();
        return bookRepository.findByCategoriesContainingIgnoreCase(categoria)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    public List<BookDTO> buscadorPorLenguage(String lenguage) {
        if (lenguage == null || lenguage.trim().isEmpty()) return new ArrayList<>();
        return bookRepository.findByLanguageContainingIgnoreCase(lenguage)
                .stream()
                .map(this::mapearADto)
                .toList();
    }


    @Override
    public Optional<BookDTO> buscarLibroPorIdGoogle(String googleId) {

        return bookRepository.findByGoogleId(googleId)
                .map(this::mapearADto);
    }

    @Override
    public List<BookDTO> buscarEnGoogleBooks(String filtro, int pagina, String lang) {
        if (filtro == null || filtro.trim().isEmpty()) return new ArrayList<>();

        int maxResults = 40;
        int startIndex = pagina * maxResults;
        String idioma = (lang == null || lang.trim().isEmpty()) ? "es" : lang;
        String apiKey = "AIzaSyCQHkjXip6an8V7K26WUrQmYaxaU34h5Mw";

        try {
            String busquedaCodificada = java.net.URLEncoder.encode(filtro.trim(), java.nio.charset.StandardCharsets.UTF_8);

            String url = "https://www.googleapis.com/books/v1/volumes?q=" + busquedaCodificada
                    + "&printType=books"
                    + "&langRestrict=" + idioma
                    + "&orderBy=relevance"
                    + "&maxResults=" + maxResults
                    + "&startIndex=" + startIndex
                    + "&key=" + apiKey;

            String jsonResponse = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);

            if (root == null || !root.has("items")) return new ArrayList<>();

            JsonNode items = root.get("items");
            List<BookDTO> listaLibros = new ArrayList<>();

            if (items.isArray()) {
                for (JsonNode item : items) {
                    JsonNode info = item.get("volumeInfo");
                    if (info == null) continue;

                    String isbn = "N/A";
                    if (info.has("industryIdentifiers")) {
                        for (JsonNode idNode : info.get("industryIdentifiers")) {
                            if (idNode.get("type").asText().startsWith("ISBN")) {
                                isbn = idNode.get("identifier").asText();
                                break;
                            }
                        }
                    }

                    String imageUrl = "https://placehold.co/400x600?text=Sin+Portada";
                    if (info.has("imageLinks")) {
                        JsonNode images = info.get("imageLinks");
                        if (images.has("extraLarge")) imageUrl = images.get("extraLarge").asText();
                        else if (images.has("large")) imageUrl = images.get("large").asText();
                        else if (images.has("medium")) imageUrl = images.get("medium").asText();
                        else if (images.has("thumbnail")) imageUrl = images.get("thumbnail").asText();
                        imageUrl = imageUrl.replace("http:", "https:").replace("&edge=curl", "");
                    }

                    String category = "General";
                    if (info.has("categories") && info.get("categories").isArray() && info.get("categories").size() > 0) {
                        category = info.get("categories").get(0).asText();
                    }

                    listaLibros.add(new BookDTO(
                            null,
                            item.has("id") ? item.get("id").asText() : "",
                            isbn,
                            info.has("title") ? info.get("title").asText() : "Sin Título",
                            info.has("authors") && info.get("authors").size() > 0 ? info.get("authors").get(0).asText() : "Autor Desconocido",
                            info.has("publisher") ? info.get("publisher").asText() : "S/E",
                            info.has("publishedDate") ? info.get("publishedDate").asText() : "2000-01-01",
                            info.has("description") ? info.get("description").asText().substring(0, Math.min(info.get("description").asText().length(), 3000)) : "Sin descripción",
                            info.has("pageCount") ? info.get("pageCount").asInt() : 0,
                            category,
                            imageUrl,
                            info.has("language") ? info.get("language").asText() : "es",
                            info.has("printType") ? info.get("printType").asText() : "BOOK",
                            info.has("previewLink") ? info.get("previewLink").asText() : ""
                    ));
                }
            }
            return listaLibros;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    //Guardar algo debo convertirlo a entity
    private Book mapearAEntidad(BookDTO bookDTO){
        Book book = new Book();
        book.setGoogleId(bookDTO.googleId());
        book.setIsbn(bookDTO.isbn());
        book.setTitle(bookDTO.title());
        book.setAuthors(bookDTO.authors());
        book.setPublisher(bookDTO.publisher());
        book.setPublishedDate(bookDTO.publishedDate());
        book.setDescription(bookDTO.description());
        book.setPageCount(bookDTO.pageCount());
        book.setCategories(bookDTO.categories());
        book.setImageUrl(bookDTO.imageUrl());
        book.setLanguage(bookDTO.language());
        book.setPrintType(bookDTO.printType());
        book.setPreviewLink(bookDTO.previewLink());
        return book;
    }
    //Cuando devuelvo algo de la bd devo enviarlo como dto
    private BookDTO mapearADto(Book book){
        return new BookDTO(
                book.getId(),
                book.getGoogleId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthors(),
                book.getPublisher(),
                book.getPublishedDate(),
                book.getDescription(),
                book.getPageCount(),
                book.getCategories(),
                book.getImageUrl(),
                book.getLanguage(),
                book.getPrintType(),
                book.getPreviewLink()
        );
    }
}
