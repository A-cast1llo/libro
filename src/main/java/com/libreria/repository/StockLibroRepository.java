package com.libreria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libreria.models.Libro;
import com.libreria.models.StockLibro;


public interface StockLibroRepository extends JpaRepository<StockLibro, Long>{

	Optional<StockLibro> findByLibro(Libro libro);
	
	List<StockLibro> findByCantidadTotalGreaterThanEqual(int cantidad);
	
	Optional<StockLibro> findByLibroTituloIgnoreCase(String titulo);

}
