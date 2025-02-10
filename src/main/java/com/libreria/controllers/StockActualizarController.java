package com.libreria.controllers;

import com.libreria.dto.StockLibroDto;
import com.libreria.models.StockLibro;
import com.libreria.repository.StockLibroRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
public class StockActualizarController {

	@Autowired
	private StockLibroRepository stockLibroRepository;

	@PutMapping("/actualizar")
	public ResponseEntity<StockLibro> actualizarStock(@RequestBody StockLibroDto stockLibroDto) {

	    if (stockLibroDto.getCantidad() == null || stockLibroDto.getCantidad() < 0) {
	        return ResponseEntity.badRequest().build();
	    }

	    // Busca el id del stock por nombre del libro
	    Optional<StockLibro> stockOptional = stockLibroRepository.findByLibroTituloIgnoreCase(stockLibroDto.getTituloLibro());

	    // Verificar relacion con id 
	    if (stockOptional.isEmpty()) {
	        return ResponseEntity.badRequest().build();
	    }

	    // Actualizar el stock del libro encontrado
	    StockLibro stockLibro = stockOptional.get();
	    stockLibro.setCantidadTotal(stockLibroDto.getCantidad());
	    StockLibro stockActualizado = stockLibroRepository.save(stockLibro);

	  
	    return ResponseEntity.ok(stockActualizado);
	}
}