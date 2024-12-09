package com.libreria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreria.dto.LibroDto;
import com.libreria.models.Autor;
import com.libreria.models.Libro;
import com.libreria.repository.AutorRepository;
import com.libreria.repository.LibroRepository;

@RestController
@RequestMapping("/apiv1/libros")
@CrossOrigin(origins = "http://localhost:5173")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @GetMapping("/listar")
    public List<Libro> listarLibros() {
    	return libroRepository.findAll();
    }
    
    @PostMapping
    public Libro crearLibro(@RequestBody LibroDto libroDTO) {
        Autor autor = autorRepository.findById(libroDTO.getIdAutor())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        Libro libro = new Libro();
        libro.setTitulo(libroDTO.getTitulo());
        libro.setFechaPublicacion(libroDTO.getFechaPublicacion());
        libro.setAutor(autor);

        return libroRepository.save(libro);
    }
}
