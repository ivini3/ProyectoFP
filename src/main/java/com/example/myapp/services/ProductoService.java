package com.example.myapp.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.domain.Producto;

public interface ProductoService {
    Producto añadirProducto(Producto producto,MultipartFile file);
     void eliminarProducto(Producto producto);
    List<Producto> obtenerProductos();
     Producto obtenerporId(long id);
     Producto editarProducto(Producto producto,MultipartFile file);
     void borrarImagen(Long id);
     List<Producto> obtenerProductosPorCat (long id);
     Producto obtenerPorNombre(String nombre);
}
