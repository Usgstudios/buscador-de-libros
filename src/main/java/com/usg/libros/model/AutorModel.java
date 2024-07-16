package com.usg.libros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class AutorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String nombre;
    private String nacimiento;
    private String muerte;
    @ManyToOne
    private LibroModel libros;

    public AutorModel(){}

    public AutorModel(String nombre, String nacimiento, String muerte) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.muerte = muerte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getMuerte() {
        return muerte;
    }

    public void setMuerte(String muerte) {
        this.muerte = muerte;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public LibroModel getLibros() {
        return libros;
    }

    public void setLibros(LibroModel libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return
                "Nombre= " + nombre + '\n' +
                "Nacimiento= " + nacimiento + '\n' +
                "Muerte= " + muerte + '\n';
    }
}
