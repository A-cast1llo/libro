package com.libreria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.models.Estado;
import com.libreria.repository.EstadoRepository;

@RestController
@RequestMapping("/apiv1/estados")
@CrossOrigin(origins = "http://localhost:5173") 
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping("/listar")
    public List<Estado> listarEstados() {
        return estadoRepository.findAll();
    }
}