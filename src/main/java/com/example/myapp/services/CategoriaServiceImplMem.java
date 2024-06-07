package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.domain.Categoria;
import com.example.myapp.repository.CategoriaRepository;
@Service
public class CategoriaServiceImplMem implements CategoriaService {
    @Autowired CategoriaRepository repositorio;
    

    public void añadirCategoria(Categoria categoria) {
        repositorio.save(categoria);
    }

    public void eliminarCategoria(Categoria categoria) {
        repositorio.delete(categoria);
    }
    public void eliminarCategoriaPorId(Long id){
        repositorio.deleteById(id);
    }

    public List<Categoria> obtenerCategorias() {
        return repositorio.findAll();
    }

    public Categoria obtenerporId(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Categoria editarCategoria(Categoria categoria) {
        return repositorio.save(categoria);
    }
    public Categoria obtenerPorNombre (String nombre){
        return repositorio.findByNombre(nombre);
    }
}


