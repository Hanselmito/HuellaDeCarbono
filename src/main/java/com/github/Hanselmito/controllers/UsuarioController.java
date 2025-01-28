package com.github.Hanselmito.controllers;

import com.github.Hanselmito.services.UsuarioService;
import com.github.Hanselmito.entities.Usuario;
import javafx.scene.control.Alert;

public class UsuarioController {
    private UsuarioService usuarioService = new UsuarioService();

    public boolean comprobarCredenciales(String identifier, String contrasena) {
        try {
            Usuario usuario = usuarioService.login(identifier, contrasena);
            return usuario != null;
        } catch (Exception e) {
            showAlert("Error de autenticación", e.getMessage());
            return false;
        }
    }

    public void addUsuario(Usuario usuario) throws Exception {
        try {
            usuarioService.addUsuario(usuario);
            showAlert("Registro exitoso", "Usuario registrado correctamente.");
        } catch (Exception e) {
            showAlert("Error de registro", e.getMessage());
            throw e;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}