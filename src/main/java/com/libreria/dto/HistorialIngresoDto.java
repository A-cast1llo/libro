package com.libreria.dto;

import lombok.Data;

@Data
public class HistorialIngresoDto {

	private Long idLibro;
    private Integer cantidad;
    private String motivo;
}
