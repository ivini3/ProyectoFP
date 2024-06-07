package com.example.myapp.services;

import java.util.List;

import com.example.myapp.domain.Categoria;

public interface CategoriaService {
    void añadirCategoria(Categoria categoria);
    void eliminarCategoria(Categoria categoria);
    void eliminarCategoriaPorId(Long id);
    List<Categoria> obtenerCategorias();
    Categoria obtenerporId(Long id);
    Categoria editarCategoria(Categoria categoria);
    Categoria obtenerPorNombre (String nombre);
}
