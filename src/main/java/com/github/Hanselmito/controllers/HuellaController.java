package com.github.Hanselmito.controllers;

import com.github.Hanselmito.services.HuellaService;
import com.github.Hanselmito.entities.Huella;
import javafx.scene.control.Alert;

public class HuellaController {
    private HuellaService huellaService = new HuellaService();

    public void addHuella(Huella huella) throws Exception {
        try {
            huellaService.addHuella(huella);
            showAlert("Registro exitoso", "Huella registrada correctamente.");
        } catch (Exception e) {
            showAlert("Error de registro", e.getMessage());
            throw e;
        }
    }

    public void updateHuella(Huella huella) throws Exception {
        try {
            huellaService.updateHuella(huella);
            showAlert("Actualización exitosa", "Huella actualizada correctamente.");
        } catch (Exception e) {
            showAlert("Error de actualización", e.getMessage());
            throw e;
        }
    }

    public void deleteHuella(Huella huella) throws Exception {
        try {
            huellaService.deleteHuella(huella);
            showAlert("Eliminación exitosa", "Huella eliminada correctamente.");
        } catch (Exception e) {
            showAlert("Error de eliminación", e.getMessage());
            throw e;
        }
    }

    public Huella findHuellaById(int id) throws Exception {
        try {
            return huellaService.findHuellaById(id);
        } catch (Exception e) {
            showAlert("Error de búsqueda", e.getMessage());
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