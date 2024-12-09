package com.libreria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libreria.models.Libro;
import com.libreria.models.StockLibro;

public interface StockLibroRepository extends JpaRepository<StockLibro, Long>{

	Optional<StockLibro> findByLibro(Libro libro);
}
