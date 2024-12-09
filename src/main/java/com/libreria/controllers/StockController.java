package com.libreria.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.dto.HistorialIngresoDto;
import com.libreria.dto.StockLibroDto;
import com.libreria.models.HistorialIngreso;
import com.libreria.models.Libro;
import com.libreria.models.StockLibro;
import com.libreria.repository.HistorialIngresoRepository;
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
	
	
	
	@GetMapping("/listar")
	/*public List<StockLibro> listarStock(){
		return stockLibroRepository.findAll();
	}*/
	public ResponseEntity<List<StockLibroDto>> listarStock() {
	    List<StockLibroDto> stockList = stockLibroRepository.findAll().stream()
	        .map(stock -> new StockLibroDto(
	            stock.getLibro().getTitulo(),
	            stock.getCantidadTotal()
	        ))
	        .toList();

	    return ResponseEntity.ok(stockList);
	}
	
	
	 @PostMapping("/registrar")
	    public ResponseEntity<String> registrarStock(@RequestBody HistorialIngresoDto ingresoDto) {
	        // Buscar el libro por ID
	        Libro libro = libroRepository.findById(ingresoDto.getIdLibro())
	                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

	        // Actualizar o crear registro en StockLibro
	        StockLibro stockLibro = stockLibroRepository.findByLibro(libro)
	                .orElseGet(() -> {
	                    StockLibro nuevoStock = new StockLibro();
	                    nuevoStock.setLibro(libro);
	                    nuevoStock.setCantidadTotal(0); // Inicializa con 0
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
}
