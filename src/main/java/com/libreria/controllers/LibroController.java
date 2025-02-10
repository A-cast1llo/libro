package com.libreria.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.libreria.dto.LibroDto;
import com.libreria.models.Autor;
import com.libreria.models.Libro;
import com.libreria.models.StockLibro;
import com.libreria.repository.AutorRepository;
import com.libreria.repository.LibroRepository;
import com.libreria.repository.StockLibroRepository;

@RestController
@RequestMapping("/apiv1/libros")
@CrossOrigin(origins = "http://localhost:5173")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private StockLibroRepository stockLibroRepository;

    @GetMapping("/listar")
    public List<Libro> listarLibros() {
        return libroRepository.findByActivoTrue();
    }
    
    @GetMapping("/total")
    public long contarLibros() {
        return libroRepository.count(); 
    }
    
    @PostMapping
    public ResponseEntity<Libro> registrarLibro(@RequestBody LibroDto libroDto) {
        Optional<Libro> libroExistente = libroRepository.findByTitulo(libroDto.getTitulo());

        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            
            if (libro.getActivo() == 0) {
                libro.setActivo(1); // Cambia estado a libro
                libroRepository.save(libro);
                return ResponseEntity.ok(libro);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El libro ya está registrado y activo.");
            }
        }

        // Crear nuevo si no se encontro registros
        Libro nuevoLibro = new Libro();
        nuevoLibro.setTitulo(libroDto.getTitulo());
        nuevoLibro.setFechaPublicacion(libroDto.getFechaPublicacion());
        nuevoLibro.setAutor(autorRepository.findById(libroDto.getIdAutor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado")));
        nuevoLibro.setActivo(1);

        libroRepository.save(nuevoLibro);
        return ResponseEntity.ok(nuevoLibro);
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
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarLibro(@PathVariable Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));

        // Consultar el stock del libro en la entidad StockLibro
        Optional<StockLibro> stockLibroOptional = stockLibroRepository.findByLibro(libro);

        if (stockLibroOptional.isPresent() && stockLibroOptional.get().getCantidadTotal() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede eliminar el libro porque tiene stock disponible.");
        }


        
     // En lugar de eliminar, marcamos como inactivo
        libro.setActivo(0);
        libroRepository.save(libro);
        
        return ResponseEntity.ok("Libro eliminado correctamente");
    }
}
