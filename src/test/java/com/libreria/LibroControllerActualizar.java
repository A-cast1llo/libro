package com.libreria;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.libreria.controllers.LibroController;
import com.libreria.dto.LibroDto;
import com.libreria.models.Autor;
import com.libreria.models.Libro;
import com.libreria.repository.AutorRepository;
import com.libreria.repository.LibroRepository;

class LibroControllerActualizar {
	
	@Mock
	private LibroRepository libroRepository;
	@Mock
	private AutorRepository autorRepository;
	@InjectMocks
	private LibroController libroController;
	
	public LibroControllerActualizar() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
    void testEditarLibro() {
        // Datos de entrada
        Long idLibro = 1L;

        LibroDto libroDto = new LibroDto();
        libroDto.setTitulo("Nuevo Título");
        libroDto.setFechaPublicacion(LocalDate.parse("2025-01-21"));
        libroDto.setIdAutor(2L);

        // Base de datos simulada
        Libro libroExistente = new Libro();
        libroExistente.setIdLibro(idLibro);
        libroExistente.setTitulo("Título Antiguo");
        libroExistente.setFechaPublicacion(LocalDate.parse("2020-01-01"));

        Autor autorMock = new Autor();
        autorMock.setIdAutor(2L);
        autorMock.setNombre("Autor Actualizado");

        when(libroRepository.findById(idLibro)).thenReturn(Optional.of(libroExistente));
        when(autorRepository.findById(2L)).thenReturn(Optional.of(autorMock));

        
        Libro libroActualizado = new Libro();
        libroActualizado.setIdLibro(idLibro);
        libroActualizado.setTitulo("Nuevo Título");
        libroActualizado.setFechaPublicacion(LocalDate.parse("2025-01-21"));
        libroActualizado.setAutor(autorMock);

        when(libroRepository.save(any(Libro.class))).thenReturn(libroActualizado);


        Libro resultado = libroController.editarLibro(idLibro, libroDto);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals("Nuevo Título", resultado.getTitulo());
        assertEquals(LocalDate.parse("2025-01-21"), resultado.getFechaPublicacion());
        assertEquals(autorMock, resultado.getAutor());


    }
}