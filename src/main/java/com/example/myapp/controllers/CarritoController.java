package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.domain.Carrito;
import com.example.myapp.domain.Producto;
import com.example.myapp.domain.Usuario;
import com.example.myapp.services.CarritoService;
import com.example.myapp.services.ProductoService;
import com.example.myapp.services.UsuarioService;

@Controller

public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/vercarrito/{id}")
    public String verCarrito(@PathVariable("id") Long idUsuario, Model model) {
        Usuario usuario = usuarioService.obtenerporId(idUsuario);
    
        if (usuario == null) {
            model.addAttribute("mensaje", "Usuario no encontrado");
        }
    
        // Verificar si el carrito es nulo y, en ese caso, inicializarlo
        if (usuario.getCarrito() == null) {
            System.out.println("Carro vacio");
            usuario.setCarrito(new Carrito()); // Ajusta según la lógica de tu aplicación
        }
        double total = carritoService.calcularTotal(usuario.getCarrito());

        model.addAttribute("carrito", usuario.getCarrito());
        model.addAttribute("total", total); // Añade el total al modelo

        return "carritoView"; // Asegúrate de tener una vista "carritoView.html"
    }

    @PostMapping("/agregar/{id}")
    public String agregarProductoAlCarrito(@PathVariable("id") Long idProducto, Model model) {
        // Obtener la autenticación del usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("mensaje", "Usuario no autenticado");
        
        }
    
        // Obtener el nombre del usuario autenticado
        String currentUserName = authentication.getName();
        Usuario usuario = usuarioService.obtenerPorNombre(currentUserName);
    
        if (usuario == null) {
            model.addAttribute("mensaje", "Usuario no encontrado");
            
        }
    
        // Obtener el producto por su ID
        Producto producto = productoService.obtenerporId(idProducto);
        
        // Añadir el producto al carrito del usuario
        carritoService.añadirProductoAlCarrito(usuario, producto);
    
        // Redireccionar a la vista de carrito
        return "redirect:/vercarrito/"+usuario.getId(); // Redirigir a la vista de carrito del usuario
    }
    @PostMapping("/eliminar/{id}")
    public String eliminarProductoDelCarrito(@PathVariable("id") Long idProducto, Model model) {
        // Obtener la autenticación del usuario actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("mensaje", "Usuario no autenticado");

        }

        // Obtener el nombre del usuario autenticado
        String currentUserName = authentication.getName();
        Usuario usuario = usuarioService.obtenerPorNombre(currentUserName);

        if (usuario == null) {
            model.addAttribute("mensaje", "Usuario no encontrado");

        }

        // Obtener el producto por su ID
        Producto producto = productoService.obtenerporId(idProducto);

        // Eliminar el producto del carrito del usuario
        carritoService.eliminarProductoDelCarrito(usuario, producto);

        // Redireccionar a la vista de carrito
        return "redirect:/vercarrito/" + usuario.getId(); // Redirigir a la vista de carrito del usuario
    }

}


