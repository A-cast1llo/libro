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

	@PostMapping("/actualizar")
	public ResponseEntity<String> actualizarStock(@RequestBody StockLibroDto stockLibroDto) {
		// Validar cantidad
		if (stockLibroDto.getCantidad() == null || stockLibroDto.getCantidad() < 0) {
			return ResponseEntity.badRequest().body("La cantidad debe ser mayor o igual a cero.");
		}

		// Buscar el stock por titulo
		Optional<StockLibro> stockOptional = stockLibroRepository.findAll().stream()
				.filter(stock -> stock.getLibro().getTitulo().equalsIgnoreCase(stockLibroDto.getTituloLibro()))
				.findFirst();

		if (stockOptional.isEmpty()) {
			return ResponseEntity.badRequest().body(
					"El libro con título '" + stockLibroDto.getTituloLibro() + "' no está registrado en el stock.");
		}

		StockLibro stockLibro = stockOptional.get();
		
		// Stock nuevo
		stockLibro.setCantidadTotal(stockLibroDto.getCantidad());
		stockLibroRepository.save(stockLibro);

		return ResponseEntity.ok("Stock actualizado correctamente. Nuevo total: " + stockLibro.getCantidadTotal());
	}
}