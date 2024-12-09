package com.libreria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockLibroDto {

	private String tituloLibro;
    private Integer cantidad;

}
