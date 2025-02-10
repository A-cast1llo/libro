package com.libreria;

import com.libreria.controllers.StockActualizarController;
import com.libreria.dto.StockLibroDto;
import com.libreria.models.Libro;
import com.libreria.models.StockLibro;
import com.libreria.repository.StockLibroRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StockActualizarControllerTest {

    @Mock
    private StockLibroRepository stockLibroRepository;

    @InjectMocks
    private StockActualizarController stockActualizarController;

    public StockActualizarControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActualizarStock() {
        //Datos simulados
        StockLibroDto stockLibroDto = new StockLibroDto();
        stockLibroDto.setTituloLibro("La Torre Oscura I: El pistolero");
        stockLibroDto.setCantidad(5);

        Libro libro = new Libro();
        libro.setIdLibro(1L);
        libro.setTitulo("La Torre Oscura I: El pistolero");

        StockLibro stockLibro = new StockLibro();
        stockLibro.setIdStock(1L);
        stockLibro.setLibro(libro);
        stockLibro.setCantidadTotal(30); // Cantidad inicial

        // Simulación de comportamiento del repositorio
        when(stockLibroRepository.findByLibroTituloIgnoreCase("La Torre Oscura I: El pistolero"))
                .thenReturn(Optional.of(stockLibro));
        when(stockLibroRepository.save(any(StockLibro.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        
        // Llamar al método que se está probando
        ResponseEntity<StockLibro> response = stockActualizarController.actualizarStock(stockLibroDto);

        // Verificacion
        assertEquals(5, response.getBody().getCantidadTotal()); // Verificar stock actualizado

    }
}