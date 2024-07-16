package com.usg.libros.repository;

import com.usg.libros.model.LibroModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository  extends JpaRepository<LibroModel,Long> {
    Optional<LibroModel> findByTitulo(String titulo);
}
