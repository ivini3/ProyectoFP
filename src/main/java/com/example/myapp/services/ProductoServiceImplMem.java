package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.domain.Producto;
import com.example.myapp.repository.ProductoRepository;

@Service
public class ProductoServiceImplMem implements ProductoService {
    @Autowired ProductoRepository repositorio;
    @Autowired FileStorageService fileStorageService;
    public Producto añadirProducto(Producto producto,MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String nombreImagen = fileStorageService.store(file);
                producto.setImagen(nombreImagen);
            } catch (Exception e) {
                return null;
            }
        }
        return repositorio.save(producto);
    }

    public void eliminarProducto(Producto producto) {
        this.borrarImagen(producto.getId());
        repositorio.delete(producto);
    }
    public void eliminarProductoPorId(long id){
        repositorio.deleteById(id);
    }

    public List<Producto> obtenerProductos() {
        return repositorio.findAll();
    }

    public Producto obtenerporId(long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Producto editarProducto(Producto producto,MultipartFile file) {
        this.borrarImagen(producto.getId());

        if (!file.isEmpty()) {
            try {
                String nombreImagen = fileStorageService.store(file);
                producto.setImagen(nombreImagen);
            } catch (Exception e) {
                return null;
            }
        }
        return repositorio.save(producto);
    }
    public void borrarImagen(Long id) {
        Producto producto = repositorio.findById(id).orElse(null);
        if (producto != null) {
            fileStorageService.delete(producto.getImagen());
        }
    }
    public List<Producto> obtenerProductosPorCat(long id){
        return repositorio.findByCategoriaId(id);
    }
    public Producto obtenerPorNombre(String nombre){
        return repositorio.findByNombre(nombre);
    }
}
