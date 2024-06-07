package com.example.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myapp.domain.Carrito;

public interface CarritoRepository extends JpaRepository <Carrito,Long> {
   
}
