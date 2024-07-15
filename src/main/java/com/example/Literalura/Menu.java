package com.example.Literatura;

import com.example.Literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@Component
public class Menu {

    @Autowired
    private LibroService libroService;

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("-------------------------------------");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Buscar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en un determinado año");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("6 - Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = scanner.nextLine();
                    libroService.buscarLibroPorTitulo(titulo);
                    break;
                case 2:
                    libroService.buscarLibrosRegistrados();
                    break;
                case 3:
                    System.out.print("Ingrese el nombre del autor: ");
                    String autor = scanner.nextLine();
                    libroService.listarAutoresRegistrados(autor);
                    break;
                case 4:
                    System.out.print("Ingrese el año: ");
                    int año = scanner.nextInt();
                    libroService.listarAutoresVivosEnAño(año);
                    break;
                case 5:
                    Map<String, String> idiomas = libroService.obtenerIdiomasDisponibles();
                    System.out.println("Idiomas disponibles:");
                    idiomas.forEach((codigo, nombre) -> System.out.println(codigo + " - " + nombre));
                    System.out.print("Ingrese el código del idioma: ");
                    String idioma = scanner.nextLine();
                    libroService.listarLibrosPorIdioma(idioma);
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 6);
    }
}
