package com.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libreria.models.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>{

}
