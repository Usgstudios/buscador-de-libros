package com.usg.libros.service;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
