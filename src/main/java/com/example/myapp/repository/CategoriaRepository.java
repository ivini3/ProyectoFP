package com.example.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Categoria;

public interface CategoriaRepository extends JpaRepository <Categoria,Long> {
    Categoria findByNombre(String nombre);
}
