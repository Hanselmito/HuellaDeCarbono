package com.github.Hanselmito.services;

import com.github.Hanselmito.dao.UsuarioDAO;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.utils.PasswordUtil;

import java.time.LocalDate;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();


    public Usuario login(String identifier, String contrasena) throws Exception {
        String hashedPassword = PasswordUtil.hashPassword(contrasena);
        Usuario usuario = usuarioDAO.findByEmailOrUsernameAndPassword(identifier, hashedPassword);
        if (usuario == null) {
            throw new Exception("Credenciales inválidas");
        }
        return usuario;
    }

    public void addUsuario(Usuario usuario) throws Exception {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
            throw new Exception("El nombre del usuario no puede estar vacío");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new Exception("El email del usuario no puede estar vacío");
        }
        if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
            throw new Exception("La contraseña del usuario no puede estar vacía");
        }
        usuario.setContrasena(PasswordUtil.hashPassword(usuario.getContrasena()));
        usuario.setFechaRegistro(LocalDate.now());
        usuarioDAO.save(usuario);
    }

    public Usuario updateUsuario(Usuario usuario) throws Exception {
        if (usuario.getId() == null) {
            throw new Exception("El ID del usuario no puede estar vacío");
        }
        return usuarioDAO.update(usuario);
    }

    public void deleteUsuario(Usuario usuario) throws Exception {
        if (usuario.getId() == null) {
            throw new Exception("El ID del usuario no puede estar vacío");
        }
        usuarioDAO.delete(usuario);
    }

    public Usuario findUsuarioById(int id) throws Exception {
        Usuario usuario = usuarioDAO.findById(id);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado");
        }
        return usuario;
    }

    public Usuario findUsuarioByEmail(String email) throws Exception {
        Usuario usuario = usuarioDAO.findByEmail(email);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado");
        }
        return usuario;
    }

    public void updatePassword(String email, String newPassword) throws Exception {
        Usuario usuario = findUsuarioByEmail(email);
        if (usuario != null) {
            usuario.setContrasena(PasswordUtil.hashPassword(newPassword));
            updateUsuario(usuario);
        } else {
            throw new Exception("Usuario no encontrado");
        }
    }
}
