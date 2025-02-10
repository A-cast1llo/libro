package com.libreria.dto;

import lombok.Data;

@Data
public class CambioPasswordDto {
	
	private Long idUsuario;
    private String currentPassword;
    private String newPassword;
}