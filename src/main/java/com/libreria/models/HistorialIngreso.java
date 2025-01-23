package com.libreria.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Entity
@Data
public class HistorialIngreso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idIngreso;

	@ManyToOne
	@JoinColumn(name = "id_libro", nullable = false)
	private Libro libro;

	@Column(nullable = false)
	private Integer cantidad;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy - hh:mm a")
	@Column(nullable = false)
	private LocalDateTime fechaIngreso;

	@Column(nullable = false)
	private String motivo;

	

}
