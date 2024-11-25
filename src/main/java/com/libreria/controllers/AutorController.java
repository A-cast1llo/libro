package com.libreria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.models.Autor;
import com.libreria.repository.AutorRepository;

@RestController
@RequestMapping("/apiv1/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @GetMapping("/listar")
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }
    
    @PostMapping("/add")
    public Autor crearAutor(@RequestBody Autor autor) {
        return autorRepository.save(autor);
    }
}