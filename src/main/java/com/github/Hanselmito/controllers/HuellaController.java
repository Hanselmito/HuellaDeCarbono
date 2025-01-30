package com.github.Hanselmito.controllers;

import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.services.HuellaService;
import com.github.Hanselmito.entities.Huella;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

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

    public BigDecimal mostrarHuellaCarbono(Usuario usuario) {
        try {
            return huellaService.calcularHuellaCarbono(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    public Map<LocalDate, BigDecimal> mostrarHistorialHuellaCarbono(Usuario usuario, ChronoUnit periodo) {
        try {
            return huellaService.obtenerHistorialHuellaCarbono(usuario, periodo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<LocalDate, BigDecimal> compararImpactoAmbiental(Usuario usuario, ChronoUnit periodo) {
        try {
            return huellaService.compararImpactoAmbiental(usuario, periodo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}