package com.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.libreria.models.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    
}
