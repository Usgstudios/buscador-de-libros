package com.usg.libros.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class LibroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique = true)
    private String titulo;
    private String autor;
    private String idioma;
    private String descargas;
    @OneToMany(mappedBy = "libros")
    private List<AutorModel> autores;

    public LibroModel(){}

    public LibroModel(String titulo, String autor, String idioma, String descargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.descargas = descargas;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescargas() {
        return descargas;
    }

    public void setDescargas(String descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return
                "Titulo= " + titulo + '\n' +
                "Autor= " + autor + '\n' +
                "Idioma= " + idioma + '\n' +
                "Descargas= " + descargas + '\n';
    }
}
