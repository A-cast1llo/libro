package com.libreria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.models.HistorialIngreso;
import com.libreria.models.HistorialSalida;
import com.libreria.repository.HistorialIngresoRepository;
import com.libreria.repository.HistorialSalidaRepository;

@RestController
@RequestMapping("/apiv1/historial")
@CrossOrigin(origins = "http://localhost:5173")
public class HistorialIngresoController {

	@Autowired
	private HistorialIngresoRepository historialIngresoRepository;
	
	@Autowired
	private HistorialSalidaRepository historialSalidaRepository;

	@GetMapping("/ingreso")
	public ResponseEntity<List<HistorialIngreso>> listarHistorial() {
		List<HistorialIngreso> historial = historialIngresoRepository.findAll();
		return ResponseEntity.ok(historial);
	}
	
	@GetMapping("/salida")
	public ResponseEntity<List<HistorialSalida>> listarSalida(){
		List<HistorialSalida> historialSalida = historialSalidaRepository.findAll();
		return ResponseEntity.ok(historialSalida);
	}

    
    @GetMapping("/entradas/total")
    public ResponseEntity<Long> contarEntradas() {
        long totalEntradas = historialIngresoRepository.count();
        return ResponseEntity.ok(totalEntradas);
    }

    
    @GetMapping("/salidas/total")
    public ResponseEntity<Long> contarSalidas() {
        long totalSalidas = historialSalidaRepository.count();
        return ResponseEntity.ok(totalSalidas);
    }
}
