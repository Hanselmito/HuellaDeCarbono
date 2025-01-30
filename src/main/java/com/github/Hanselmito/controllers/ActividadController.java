package com.github.Hanselmito.controllers;

import com.github.Hanselmito.services.ActividadService;
import com.github.Hanselmito.entities.Actividad;
import javafx.scene.control.Alert;

public class ActividadController {
    private ActividadService actividadService = new ActividadService();

    public void addActividad(Actividad actividad) throws Exception {
        try {
            actividadService.addActividad(actividad);
            showAlert("Registro exitoso", "Actividad registrada correctamente.");
        } catch (Exception e) {
            showAlert("Error de registro", e.getMessage());
            throw e;
        }
    }

    public void updateActividad(Actividad actividad) throws Exception {
        try {
            actividadService.updateActividad(actividad);
            showAlert("Actualización exitosa", "Actividad actualizada correctamente.");
        } catch (Exception e) {
            showAlert("Error de actualización", e.getMessage());
            throw e;
        }
    }

    public void deleteActividad(Actividad actividad) throws Exception {
        try {
            actividadService.deleteActividad(actividad);
            showAlert("Eliminación exitosa", "Actividad eliminada correctamente.");
        } catch (Exception e) {
            showAlert("Error de eliminación", e.getMessage());
            throw e;
        }
    }

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