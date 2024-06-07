package com.example.myapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.domain.Categoria;
import com.example.myapp.domain.Producto;
import com.example.myapp.domain.Usuario;
import com.example.myapp.repository.UsuarioRepository;
import com.example.myapp.services.CategoriaServiceImplMem;
import com.example.myapp.services.FileStorageService;
import com.example.myapp.services.ProductoServiceImplMem;

@Controller
public class ProductosController {
    @Autowired
   CategoriaServiceImplMem categoriaService;
     @Autowired
   ProductoServiceImplMem productoService;
   @Autowired FileStorageService fileStorageService;
   @Autowired UsuarioRepository usuarioRepository;
   @GetMapping("/productos")
   public String products(Model model) {
      model.addAttribute("todoslosproductos", productoService.obtenerProductos());
      model.addAttribute("listaCategorias", categoriaService.obtenerCategorias());
      model.addAttribute("categoriaSeleccionada", new Categoria (0,"Todas"));
      
      return "productosView";
   }

   @GetMapping("/borrarProducto/{id}")
   public String borrarproducto(@PathVariable long id) {
      productoService.eliminarProductoPorId(id);
      return "redirect:/productos";
   }

   @GetMapping("/editarProducto/{id}")
   public String editarProducto(@PathVariable long id, Model model) {
      model.addAttribute("listaCategorias", categoriaService.obtenerCategorias());
      Producto producto = productoService.obtenerporId(id);
      if (producto != null) {
         model.addAttribute("productoForm", producto);
         return "editarProductoView";
      } else {
         return "redirect:/";
      }
   }


   @PostMapping("/producto/editar/submit")
   public String showEditSubmit(Producto producto,@RequestParam MultipartFile file) {

      productoService.editarProducto(producto,file);
      return "redirect:/";
   }

   @GetMapping("/nuevoProducto")
   public String nuevoProducto(Model model) {
      model.addAttribute("productoForm", new Producto());
      model.addAttribute("listaCategorias", categoriaService.obtenerCategorias());
      return "nuevoProductoView";
   }
   
   @PostMapping("/nuevoProducto/submit")
   public String productoagregado(Producto producto,@RequestParam MultipartFile file) {
      productoService.añadirProducto(producto,file);
      return "redirect:/";
   }
   @GetMapping("/{idCat}")
public String showListInCategory(@PathVariable Long idCat, Model model) {
   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   if (!(authentication instanceof AnonymousAuthenticationToken)) {
String currentUserName = authentication.getName();
Usuario usuario = usuarioRepository.findByNombre(currentUserName);
    
    // Añade el objeto de usuario completo al modelo
    model.addAttribute("usuario", usuario);}
model.addAttribute("listaProductos", productoService.obtenerProductosPorCat(idCat));
model.addAttribute("listaCategorias", categoriaService.obtenerCategorias());
model.addAttribute("categoriaSeleccionada", categoriaService.obtenerporId(idCat));
return "indexView";
}
@GetMapping("/files/{filename:.+}")
@ResponseBody
public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    Resource file = fileStorageService.loadAsResource(filename);
    return ResponseEntity.ok().body(file);
}
}
