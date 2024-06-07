package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.myapp.domain.Categoria;
import com.example.myapp.services.CategoriaServiceImplMem;
import com.example.myapp.services.ProductoServiceImplMem;
@Controller
public class CategoriaController {
     @Autowired
   CategoriaServiceImplMem categoriaService;
     @Autowired
   ProductoServiceImplMem productoService;
     @GetMapping("/categorias")
   public String showCategoria(Model model) {
      model.addAttribute("todaslascategorias", categoriaService.obtenerCategorias());
      return "categoriaView";
   }

   @GetMapping("/borrarCategoria/{id}")
   public String borrarcategoria(@PathVariable long id,Model model) {
      if (productoService.obtenerProductosPorCat(id).size() == 0){
categoriaService.eliminarCategoriaPorId(id);
return "redirect:/";
      }
      else{
         model.addAttribute("error", "Hay productos en esta categoria sin guardar");
         model.addAttribute("todaslascategorias", categoriaService.obtenerCategorias());
      return "categoriaView";
      }
      
      
      
   }

   @GetMapping("/editarCategoria/{id}")
   public String editarCategoria(@PathVariable long id, Model model) {
      Categoria categoria = categoriaService.obtenerporId(id);
      if (categoria != null) {
         model.addAttribute("categoriaForm", categoria);
         return "editarCategoriaView";
      } else {
         return "redirect:/categoria";
      }
   }

   @PostMapping("/categoria/editar/submit")
   public String showEditSubmit(@ModelAttribute Categoria categoria) {

      categoriaService.editarCategoria(categoria);
      return "redirect:/categoria";
   }

   @GetMapping("/nuevaCategoria")
   public String nuevoCategoria(Model model) {
      model.addAttribute("categoriaForm", new Categoria());
      return "nuevaCategoriaView";
   }

   @PostMapping("/nuevaCategoria/submit")
   public String categoriaagregado(@ModelAttribute Categoria categoria) {
      categoriaService.añadirCategoria(categoria);
      return "redirect:/";
   }
   @GetMapping("/delete/{id}")
   public String showDelete(@PathVariable long id) {
   if (productoService.obtenerProductosPorCat(id).size() == 0)
   categoriaService.eliminarCategoriaPorId(id);
   return "redirect:/";
   }
}
