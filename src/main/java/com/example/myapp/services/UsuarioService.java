package com.example.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.domain.Usuario;
import com.example.myapp.dto.ContraseñaDto;
import com.example.myapp.repository.UsuarioRepository;
@Service
public class UsuarioService {
    @Autowired UsuarioRepository repositorio;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired FileStorageService fileStorageService;
    public Usuario añadirUsuario (Usuario usuario,MultipartFile file) {
        if (repositorio.findByNombre(usuario.getNombre()) != null)
        return null; //ya existe ese nombre de usuario
        String passCrypted = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passCrypted);
        if (!file.isEmpty()) {
            try {
                String nombreImagen = fileStorageService.store(file);
                usuario.setImagen(nombreImagen);
            } catch (Exception e) {
                return null;
            }
        }
        return repositorio.save(usuario);
        }

    public void eliminarUsuario(Usuario usuario) {
        repositorio.delete(usuario);
    }
    public void eliminarUsuarioPorId(long id){
        repositorio.deleteById(id);
    }

    public List<Usuario> obtenerUsuarios() {
        return repositorio.findAll();
    }

    public Usuario obtenerporId(long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Usuario editarUsuario(Usuario usuario,MultipartFile file) {
        Usuario otroUsuario = repositorio.findByNombre(usuario.getNombre());
        if (otroUsuario != null && otroUsuario.getId() != usuario.getId())
        return null; // ya existe otro usuario con ese nombre
        String passCrypted = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passCrypted);
        this.borrarImagen(usuario.getId());

        if (!file.isEmpty()) {
            try {
                String nombreImagen = fileStorageService.store(file);
                usuario.setImagen(nombreImagen);
            } catch (Exception e) {
                return null;
            }
        }
        return repositorio.save(usuario);
        }
        public Usuario editarUsuarioContraseña(Usuario usuario) {
            Usuario otroUsuario = repositorio.findByNombre(usuario.getNombre());
            if (otroUsuario != null && otroUsuario.getId() != usuario.getId())
            return null; // ya existe otro usuario con ese nombre
            String passCrypted = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passCrypted);
          
            return repositorio.save(usuario);
            }
    public Usuario obtenerPorNombre(String nombre){
        return repositorio.findByNombre (nombre);
    }
      public Usuario obtenerUsuarioConectado (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Usuario user = obtenerPorNombre(authentication.getName());
            return user;
        }
        return null;
    }
    public Usuario convertDtoToUsuario(ContraseñaDto userDto){
        Usuario userLogueado = obtenerUsuarioConectado();
        if (userLogueado != null){
            userLogueado.setPassword(userDto.getContraseña());
            editarUsuarioContraseña(userLogueado);}
        return userLogueado;
    }
        public void borrarImagen(Long id) {
        Usuario usuario = repositorio.findById(id).orElse(null);
        if (usuario != null) {
            fileStorageService.delete(usuario.getImagen());
        }
    }
}
