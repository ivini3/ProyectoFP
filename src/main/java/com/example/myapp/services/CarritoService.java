package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.domain.Carrito;
import com.example.myapp.domain.Producto;
import com.example.myapp.domain.Usuario;
import com.example.myapp.repository.CarritoRepository;
import com.example.myapp.repository.UsuarioRepository;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void añadirProductoAlCarrito(Usuario usuario, Producto producto) {
        // Verificar si el producto está en stock
        if (producto.getStock() <= 0) {
            throw new IllegalArgumentException("El producto seleccionado está agotado.");
        }

        // Obtener el carrito del usuario
        Carrito carrito = usuario.getCarrito();
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setUsuario(usuario);
            usuario.setCarrito(carrito);
        }

        // Obtener la lista de productos del carrito
        List<Producto> productosEnCarrito = carrito.getProductos();

        // Buscar si el producto ya está en el carrito
        boolean productoEncontrado = false;
        for (Producto p : productosEnCarrito) {
            if (p.getId()==(producto.getId())) {
                // Si el producto ya está en el carrito, incrementar la cantidad
                p.setCantidad(p.getCantidad() + 1);
                productoEncontrado = true;
                break;
            }
        }

        if (!productoEncontrado) {
            // Si el producto no está en el carrito, agregarlo con cantidad 1
            producto.setCantidad(1);
            productosEnCarrito.add(producto);
        }
     // Actualizar la cantidad total de productos en el carrito
     carrito.setTotalCantidad(productosEnCarrito.stream().mapToInt(Producto::getCantidad).sum());

        // Asignar el carrito al producto
        producto.setCarrito(carrito);

        // Restar el producto del stock
        producto.setStock(producto.getStock() - 1);

        // Guardar los cambios en la base de datos

        usuarioRepository.save(usuario);
    }


    public void eliminarProductoDelCarrito(Usuario usuario, Producto producto) {
        // Verificar si el carrito del usuario no es nulo y contiene el producto
        if (usuario.getCarrito() != null && usuario.getCarrito().getProductos().contains(producto)) {
            // Remover el producto del carrito
            usuario.getCarrito().getProductos().remove(producto);
            // Asignar null al carrito del producto
            producto.setCarrito(null);
            // Incrementar el stock del producto
            producto.setStock(producto.getStock() + 1);
            // Guardar los cambios en la base de datos
            usuarioRepository.save(usuario);
        }
    }

    public double calcularTotal(Carrito carrito) {
        // Verificar si el carrito es null
        if (carrito == null) {
            // Si el carrito es null, devolver 0 como total
            return 0.0;
        }
    
        // Obtener la lista de productos del carrito
        List<Producto> productos = carrito.getProductos();
        // Verificar si la lista de productos es null
        if (productos == null) {
            // Si la lista de productos es null, devolver 0 como total
            return 0.0;
        }
    
        // Calcular el total
        double total = 0.0;
        for (Producto producto : productos) {
            total += producto.getPrecio() * producto.getCantidad();
        }
        return total;
    }
    
    
}
