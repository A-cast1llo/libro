package com.libreria.dto;

import lombok.Data;

@Data
public class UsuarioDto {

    private String nombre;
    private String apellidos;
    private String usuario;
    private String password;
    private String correo;
    private String telefono;
    private Long idRol; // ID del rol
    private Long idEstado; // ID del estado
}
