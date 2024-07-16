package com.usg.libros.repository;

import com.usg.libros.model.AutorModel;
import com.usg.libros.model.LibroModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorModel,Long> {
    Optional<AutorModel> findByNombre(String nombre);
}
