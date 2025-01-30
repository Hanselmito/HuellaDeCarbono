package com.github.Hanselmito.controllers;

import com.github.Hanselmito.services.ActividadService;
import com.github.Hanselmito.entities.Actividad;
import javafx.scene.control.Alert;

public class ActividadController {
    private ActividadService actividadService = new ActividadService();

    public Actividad findActividadById(int id) throws Exception {
        try {
            return actividadService.findActividadById(id);
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