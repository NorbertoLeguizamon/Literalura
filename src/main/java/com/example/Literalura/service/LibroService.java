package com.example.Literatura.service;

import com.example.Literatura.model.Book;
import com.example.Literatura.model.BookResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private final RestTemplate restTemplate;

    public LibroService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void buscarLibroPorTitulo(String titulo) {
        String url = "https://gutendex.com/books?search=" + titulo;
        BookResponse response = restTemplate.getForObject(url, BookResponse.class);
        List<Book> librosFiltrados = response.getResults().stream()
                .filter(libro -> libro.getTitle().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
        imprimirLibros(librosFiltrados);
    }

    public void buscarLibrosRegistrados() {
        String url = "https://gutendex.com/books";
        BookResponse response = restTemplate.getForObject(url, BookResponse.class);
        imprimirLibros(response.getResults());
    }

    public void listarAutoresRegistrados(String autor) {
        String url = "https://gutendex.com/books?search=" + autor;
        BookResponse response = restTemplate.getForObject(url, BookResponse.class);
        List<Book> librosFiltrados = response.getResults().stream()
                .filter(libro -> libro.getAuthors().stream()
                        .anyMatch(a -> a.getName().toLowerCase().contains(autor.toLowerCase())))
                .collect(Collectors.toList());
        imprimirLibros(librosFiltrados);
    }

    public void listarAutoresVivosEnAño(int año) {
        String url = "https://gutendex.com/books?author_year_start=" + año + "&author_year_end=" + año;
        BookResponse response = restTemplate.getForObject(url, BookResponse.class);
        imprimirLibros(response.getResults());
    }

    public void listarLibrosPorIdioma(String idioma) {
        String url = "https://gutendex.com/books?languages=" + idioma;
        BookResponse response = restTemplate.getForObject(url, BookResponse.class);
        imprimirLibros(response.getResults());
    }

    public Map<String, String> obtenerIdiomasDisponibles() {
        Map<String, String> idiomas = new HashMap<>();
        idiomas.put("en", "Inglés");
        idiomas.put("fr", "Francés");
        idiomas.put("de", "Alemán");
        idiomas.put("es", "Español");
        idiomas.put("it", "Italiano");
        // Añadir más idiomas según sea necesario
        return idiomas;
    }

    private void imprimirLibros(List<Book> libros) {
        for (Book libro : libros) {
            System.out.println("----- LIBRO -----");
            System.out.println("Título: " + libro.getTitle());
            if (!libro.getAuthors().isEmpty()) {
                System.out.println("Autor: " + libro.getAuthors().get(0).getName());
            }
            System.out.println("Idioma: " + String.join(", ", libro.getLanguages()));
            System.out.println("Número de descargas: " + libro.getDownload_count());
            System.out.println("-----------------");
        }
    }
}
