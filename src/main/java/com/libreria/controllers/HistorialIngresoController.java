package com.libreria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.models.HistorialIngreso;
import com.libreria.repository.HistorialIngresoRepository;

@RestController
@RequestMapping("/apiv1/historial")
@CrossOrigin(origins = "http://localhost:5173")
public class HistorialIngresoController {

	@Autowired
	private HistorialIngresoRepository historialIngresoRepository;
	
	@GetMapping("/ingreso")
	public ResponseEntity<List<HistorialIngreso>> listarHistorial() {
	    List<HistorialIngreso> historial = historialIngresoRepository.findAll();
	    return ResponseEntity.ok(historial);
	}

}
