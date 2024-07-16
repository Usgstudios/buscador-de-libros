package com.usg.libros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ListaLibrosModel(
        @JsonAlias("results") List<DatosLibrosModel> resultados
) {
}
