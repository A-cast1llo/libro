package com.libreria.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @GetMapping("/total")
    public long contarLibros() {
        return libroRepository.count(); 
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
    
    
    
    @PutMapping("/actualizar/{id}")
    public Libro editarLibro(@PathVariable Long id, @RequestBody LibroDto libroDTO) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        
        if (libroDTO.getTitulo() != null) {
            libro.setTitulo(libroDTO.getTitulo());
        }

        if (libroDTO.getFechaPublicacion() != null) {
            libro.setFechaPublicacion(libroDTO.getFechaPublicacion());
        }

        if (libroDTO.getIdAutor() != null) {
            Autor autor = autorRepository.findById(libroDTO.getIdAutor())
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
            libro.setAutor(autor);
        }

        return libroRepository.save(libro);
    }
    
    
}
