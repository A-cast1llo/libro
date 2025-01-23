package com.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.libreria.models.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    
}
