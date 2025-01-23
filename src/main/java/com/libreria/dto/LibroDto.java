package com.libreria.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LibroDto {
    private String titulo;
    private LocalDate fechaPublicacion;
    private Long idAutor;
   
    
}