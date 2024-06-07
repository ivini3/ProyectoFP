package com.example.myapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.domain.Rol;
import com.example.myapp.domain.Usuario;
import com.example.myapp.dto.ContraseñaDto;
import com.example.myapp.services.UsuarioService;

import jakarta.validation.Valid;
@Controller
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    
    @GetMapping({ "/usuarios" })
    public String showList(@RequestParam (required=false) Integer advice, Model model) {
        model.addAttribute("listaUsuarios", usuarioService.obtenerUsuarios());
        return "usuarios/usuariosView";
    }

    @GetMapping("/nuevoUsuario")
    public String showNew(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        return "usuarios/newUsuariosView";
    }

    @PostMapping("/nuevoUsuario/submit")
    public String showNewSubmit(
            @Valid Usuario usuarioForm,
            BindingResult bindingResult,MultipartFile file) {
        if (bindingResult.hasErrors()){
               
            return "redirect:/nuevoUsuario";}
         usuarioService.añadirUsuario(usuarioForm,file);
        return "redirect:/usuarios";
    }
@GetMapping("/autoregistro")
    public String registroUsuario(Model model) {
        
        Usuario usuario = new Usuario();
usuario.setRol(Rol.USER);
model.addAttribute("usuarioForm", usuario);
        return "usuarios/newRegistroUsuariosView";
    }
    @PostMapping("/autoregistro/submit")
    public String registroUsuario(
            @Valid Usuario usuarioForm,
            BindingResult bindingResult,MultipartFile file) {
        if (bindingResult.hasErrors()){
               
            return "redirect:/autoregistro";}
         usuarioService.añadirUsuario(usuarioForm,file);
        return "redirect:/";
    }
    @GetMapping("/editarUsuarios/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Usuario usuario;
        usuario = usuarioService.obtenerporId(id);
        if (usuario != null)
            model.addAttribute("usuarioForm", usuario);
        return "usuarios/editUsuariosView";
    }

    @PostMapping("/editarUsuarios/submit")
    public String showEditSubmit(
            @Valid Usuario usuarioForm,
            BindingResult bindingResult,@RequestParam MultipartFile file) {
        usuarioService.editarUsuario(usuarioForm,file);
        return "redirect:/usuarios";
    }

    @GetMapping("/borrarUsuarios/{id}")
    public String showDelete(@PathVariable long id) {
        usuarioService.eliminarUsuarioPorId(id);
        return "redirect:/usuarios?";
    }
    @GetMapping("/edit/userLogued")
    public String showEditLoguedForm(Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        if (usuario != null)
            model.addAttribute("usuarioForm", usuario);
        return "usuarios/editUsuariosView";
    }

    @GetMapping("edit/passLogued")
    public String showEditPassLoguedForm(Model model) {
        ContraseñaDto userDto = new ContraseñaDto();
        model.addAttribute("usuarioForm", userDto);
        return "usuarios/editPassView";
    }

    @PostMapping("edit/passLogued/submit")
    public String showEditPassLoguedForm(
            @Valid ContraseñaDto usuarioDtoForm,
            BindingResult bindingResult) {
        usuarioService.convertDtoToUsuario(usuarioDtoForm);
        return "redirect:/";
    }

}
