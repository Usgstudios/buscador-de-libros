package com.usg.libros.principal;

import com.usg.libros.model.*;
import com.usg.libros.repository.AutorRepository;
import com.usg.libros.repository.LibroRepository;
import com.usg.libros.service.ConsumoAPI;
import com.usg.libros.service.ConvertirDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvertirDatos convertirDatos = new ConvertirDatos();
    private Scanner scanner = new Scanner(System.in);
    private String json = "";
    private LibroRepository repositorio;
    private AutorRepository autorRepository;

    public Principal(LibroRepository repository,AutorRepository repositoryAutor) {
        this.repositorio = repository;
        this.autorRepository = repositoryAutor;
    }

    public void muestraElMenu() {
        while (true) {
            var menu = """
                    ********************* MENU *********************
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ************************************************
                    """;
            System.out.println(menu);
            var opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    consumoapi();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresPorAno();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    public void consumoapi(){
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = scanner.nextLine();
        json = consumoAPI.datosLibros(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = convertirDatos.obtenerDatos(json, ListaLibrosModel.class);

        Optional<DatosLibrosModel> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            DatosLibrosModel libro = libroBuscado.get();

            // Verificar si el libro ya está en la base de datos
            Optional<LibroModel> libroExistente = repositorio.findByTitulo(libro.titulo());
            if (libroExistente.isPresent()) {
                System.out.println("El libro ya existe en la base de datos.");
                return;
            }

            String nombreAutor = libro.autor().isEmpty() ? "Desconocido" : libro.autor().get(0).nombre();

            LibroModel libroModel = new LibroModel();
            libroModel.setTitulo(libro.titulo());
            libroModel.setAutor(nombreAutor);
            libroModel.setIdioma(String.valueOf(libro.idiomas()));
            libroModel.setDescargas(String.valueOf(libro.numeroDeDescargas()));

            repositorio.save(libroModel);

            Optional<AutorModel> autorexistente = autorRepository.findByNombre(nombreAutor);
            if (autorexistente.isPresent()) {
                System.out.println("autor exitente");
            }else {
                // Guardar el autor asociado al libro
                if (!libro.autor().isEmpty()) {
                    DatosAutorModel autor = libro.autor().get(0);
                    AutorModel autorModel = new AutorModel();
                    autorModel.setNombre(autor.nombre());
                    autorModel.setNacimiento(autor.fechaDeNacimiento());
                    autorModel.setMuerte(autor.fechaDeMuerte());
                    autorModel.setLibros(libroModel);
                    autorRepository.save(autorModel);
                }
            }

            System.out.println(
                    "******************** LIBRO ********************" +
                    "\n" +
                    "Titulo: " + libroBuscado.get().titulo() + "\n" +
                    "Autor: " + nombreAutor + "\n" +
                    "Idioma: " + libroBuscado.get().idiomas() + "\n" +
                    "Descargas: " + libroBuscado.get().numeroDeDescargas() +
                    "\n" +
                    "**********************************************");
        }else {
            System.out.println("Libro no encontrado");
        }
    }
    public void librosRegistrados(){
        System.out.println("******************** LIBROS ********************");

        List<LibroModel> libro = repositorio.findAll();
        libro.stream()
                .forEach(System.out::println);

        System.out.println("************************************************" + '\n');

    }

    public void autoresRegistrados(){
        System.out.println("******************* Autores *******************");

        List<AutorModel> autor = autorRepository.findAll();
        autor.stream()
                        .forEach(System.out::println);

        System.out.println("************************************************" + '\n');
    }

    public void autoresPorAno() {
        System.out.print("Ingrese el año para filtrar los autores: ");
        var ano = scanner.nextInt();
        scanner.nextLine();

        // Obtener todos los autores de la base de datos
        List<AutorModel> autores = autorRepository.findAll();

        // Filtrar los autores por el año y los siguientes usando Stream
        List<AutorModel> autoresFiltrados = autores.stream()
                .filter(autor -> Integer.parseInt(autor.getNacimiento()) >= ano)
                .collect(Collectors.toList());

        // Mostrar los autores filtrados
        System.out.println("*************************************************");
        System.out.println("Autores registrados desde el año " + ano + " en adelante");
        System.out.println("*************************************************" + '\n');
        autoresFiltrados.forEach(autor -> System.out.println("Nombre: " + autor.getNombre() + '\n' + "Año: " + autor.getNacimiento() + '\n'));
        System.out.println("************************************************");
    }

    public void librosPorIdioma() {
        var menu = """
                    ************* BUSQUEDA POR IDIOMA **************
                    1 - es - Español
                    2 - en - Ingles
                    3 - fr - Frances
                    4 - pt - Portugues
                    ************************************************
                    """;
        System.out.println(menu);
        var opcion = scanner.nextInt();
        var idioma = "";

        switch (opcion){
            case 1:
                idioma = "es";
                procesoIdioma(idioma);
                break;
            case 2:
                idioma = "en";
                procesoIdioma(idioma);
                break;
            case 3:
                idioma = "fr";
                procesoIdioma(idioma);
                break;
            case 4:
                idioma = "pt";
                procesoIdioma(idioma);
                break;
            default:
                System.out.println("Opción inválida");
        }

    }

    public void procesoIdioma(String idioma){
        List<LibroModel> libro = repositorio.findAll();

        // Filtrar los libros por el idioma usando Stream
        List<LibroModel> librosFiltrados = libro.stream()
                .filter(l -> l.getIdioma().equalsIgnoreCase("["+idioma+"]"))
                .collect(Collectors.toList());

        // Mostrar los libros filtrados
        System.out.println("***********************************");
        System.out.println("Libros registrados en el idioma " + idioma);
        System.out.println("***********************************");
        librosFiltrados.forEach(l -> System.out.println("Titulo: " + l.getTitulo() + '\n' + "Autor: " + l.getAutor() + '\n' + "Idioma: " + l.getIdioma() + '\n'));
        System.out.println("***********************************");
    }
}
