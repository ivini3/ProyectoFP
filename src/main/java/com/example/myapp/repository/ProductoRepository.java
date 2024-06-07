package com.example.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Producto;

public interface ProductoRepository extends JpaRepository <Producto,Long>{
    List<Producto> findByCategoriaId(Long idCategoria);
Producto findByNombre (String nombre);
    
}

