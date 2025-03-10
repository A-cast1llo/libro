package com.libreria.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.dto.HistorialIngresoDto;
import com.libreria.dto.HistorialSalidaDto;
import com.libreria.models.HistorialIngreso;
import com.libreria.models.HistorialSalida;
import com.libreria.models.Libro;
import com.libreria.models.StockLibro;
import com.libreria.repository.HistorialIngresoRepository;
import com.libreria.repository.HistorialSalidaRepository;
import com.libreria.repository.LibroRepository;
import com.libreria.repository.StockLibroRepository;

@RestController
@RequestMapping("/apiv1/stocklibros")
@CrossOrigin(origins = "http://localhost:5173")
public class StockController {
	@Autowired
	private StockLibroRepository stockLibroRepository;

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private HistorialIngresoRepository historialIngresoRepository;

	@Autowired
	private HistorialSalidaRepository historialSalidaRepository;

	@GetMapping("/listar")
	public List<StockLibro> listarLibrosConStock() {
	    return stockLibroRepository.findByCantidadTotalGreaterThanEqual(1); 
	}


	@PostMapping("/registrar")
	public ResponseEntity<String> registrarStock(@RequestBody HistorialIngresoDto ingresoDto) {

		Libro libro = libroRepository.findById(ingresoDto.getIdLibro())
				.orElseThrow(() -> new RuntimeException("Libro no encontrado"));

		StockLibro stockLibro = stockLibroRepository.findByLibro(libro).orElseGet(() -> {
			StockLibro nuevoStock = new StockLibro();
			nuevoStock.setLibro(libro);
			nuevoStock.setCantidadTotal(0);
			return nuevoStock;
		});

		stockLibro.setCantidadTotal(stockLibro.getCantidadTotal() + ingresoDto.getCantidad());
		stockLibroRepository.save(stockLibro);

		// Crear registro en HistorialIngreso
		HistorialIngreso historialIngreso = new HistorialIngreso();
		historialIngreso.setLibro(libro);
		historialIngreso.setCantidad(ingresoDto.getCantidad());
		historialIngreso.setFechaIngreso(LocalDateTime.now());
		historialIngreso.setMotivo(ingresoDto.getMotivo());
		historialIngresoRepository.save(historialIngreso);

		return ResponseEntity.ok("Ingreso registrado correctamente");
	}

	@PostMapping("/salidareg")
	public ResponseEntity<String> registrarStockSalida(@RequestBody HistorialSalidaDto salidaDto) {

		Libro libro = libroRepository.findById(salidaDto.getIdLibro())
				.orElseThrow(() -> new RuntimeException("Libro no encontrado"));

		StockLibro stockLibro = stockLibroRepository.findByLibro(libro).orElseGet(() -> {
			StockLibro nuevoStock = new StockLibro();
			nuevoStock.setLibro(libro);
			nuevoStock.setCantidadTotal(0);
			return nuevoStock;
		});

		// controlar salida si el stock es insuficiente
		if (stockLibro.getCantidadTotal() < salidaDto.getCantidad()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Stock insuficiente. Stock actual: " + stockLibro.getCantidadTotal());
		}

		stockLibro.setCantidadTotal(stockLibro.getCantidadTotal() - salidaDto.getCantidad());
		stockLibroRepository.save(stockLibro);

		// Crear registro en HistorialIngreso
		HistorialSalida historialSalida = new HistorialSalida();
		historialSalida.setLibro(libro);
		historialSalida.setCantidad(salidaDto.getCantidad());
		historialSalida.setFechaIngreso(LocalDateTime.now());
		historialSalida.setMotivo(salidaDto.getMotivo());
		historialSalidaRepository.save(historialSalida);

		return ResponseEntity.ok("Salida registrada correctamente");
	}
}