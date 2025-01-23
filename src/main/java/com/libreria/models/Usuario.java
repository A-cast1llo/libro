package com.libreria.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String apellidos;

	@Column(nullable = false, unique = true)
	private String usuario;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String correo;

	@Column(nullable = true)
	private String telefono;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_rol", nullable = false)
	private Rol rol;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estado", nullable = false)
	private Estado estado;

}
