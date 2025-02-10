package com.libreria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libreria.models.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{

    long count();
    
    List<Libro> findByActivoTrue();
    Optional<Libro> findByTitulo(String titulo);

}
