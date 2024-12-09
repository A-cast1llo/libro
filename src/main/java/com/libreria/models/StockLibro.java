package com.libreria.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StockLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStock;

    @OneToOne
    @JoinColumn(name = "id_libro", nullable = false, unique = true)
    private Libro libro;

    @Column(nullable = false)
    private Integer cantidadTotal;
}
