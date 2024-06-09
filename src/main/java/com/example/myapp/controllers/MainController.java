package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.myapp.domain.Contactanos;
import com.example.myapp.domain.Usuario;
import com.example.myapp.repository.UsuarioRepository;
import com.example.myapp.services.CategoriaServiceImplMem;
import com.example.myapp.services.ContactanosService;
import com.example.myapp.services.ProductoServiceImplMem;



@Controller

public class MainController {
   @Autowired
   ProductoServiceImplMem productoService;
   @Autowired
   CategoriaServiceImplMem categoriaService;
   @Autowired UsuarioRepository usuarioRepository;
   @Autowired ContactanosService contactanosService;
   
   @GetMapping({ "/", "/home","/accesController"})
   public String showHomePath(Model model) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   model.addAttribute("listaCategorias", categoriaService.obtenerCategorias());
   model.addAttribute("listaProductos", productoService.obtenerProductos());
   if (!(authentication instanceof AnonymousAuthenticationToken)) {
String currentUserName = authentication.getName();
Usuario usuario = usuarioRepository.findByNombre(currentUserName);
    
    // Añade el objeto de usuario completo al modelo
    model.addAttribute("usuario", usuario);
    
   }
      return "indexView";
   }

   @GetMapping("/signin")
public String showLogin() { return "signinView"; }

  @GetMapping("/construccion")
  public String getMethodName() {
      return  "construccion";
  }

  @GetMapping("/quienessomos")
  public String mostrarQuienesSomos(Model model) {
      return "quienes-somosView";
  }
  @GetMapping("/contactanos")
    public String mostrarFormularioIncidencia(Model model) {
        model.addAttribute("contactaForm", new Contactanos());
        return "contactanosView";
    }
   @PostMapping("/contactanosresuelto")
    public String enviarIncidencia(@ModelAttribute Contactanos contactanos) {
        contactanosService.procesarIncidencia(contactanos);
        return "contactanosresuelto"; // Redirigir a una página de resultado después de enviar la incidencia
    }
}
