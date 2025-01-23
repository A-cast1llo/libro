package com.libreria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.models.Rol;
import com.libreria.repository.RolRepository;

@RestController
@RequestMapping("/apiv1/roles")
@CrossOrigin(origins = "http://localhost:5173") // Permite solicitudes CORS desde el frontend
public class RolController {

    @Autowired
    private RolRepository rolRepository;

    // Método para listar todos los roles
    @GetMapping("/listar")
    public List<Rol> listarRoles() {
        return rolRepository.findAll(); // Devuelve la lista de roles desde la base de datos
    }
}
