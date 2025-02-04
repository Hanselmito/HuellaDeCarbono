package com.github.Hanselmito.view;

import com.github.Hanselmito.controllers.RecomendacionController;
import com.github.Hanselmito.entities.Habito;
import com.github.Hanselmito.entities.Recomendacion;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.services.HabitoService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RecomendacionesController extends Controller implements Initializable {

    @FXML
    private VBox recomendacionesVBox;

    private Usuario currentUser;

    private RecomendacionController recomendacionController = new RecomendacionController();

    @Override
    public void onOpen(Object input) throws Exception {
        if (input instanceof Usuario) {
            currentUser = (Usuario) input;
            loadRecomendaciones(currentUser);
        } else {
            throw new Exception("El input no es una instancia de Usuario");
        }
    }

    @Override
    public void onClose(Object output) {

    }

    private void loadRecomendaciones(Usuario usuario) {
        try {
            List<Recomendacion> recomendaciones = recomendacionController.obtenerRecomendacionesPorHabitosPorCategoria(usuario);
            recomendacionesVBox.getChildren().clear();

            List<Habito> habitos = new HabitoService().findHabitosByUsuario(usuario);

            for (Habito habito : habitos) {
                String habitoInfo = String.format("Actividad: %s, Frecuencia: %d, Tipo: %s, Última Fecha: %s",
                        habito.getIdActividad().getNombre(),
                        habito.getFrecuencia(),
                        habito.getTipo().toString(),
                        habito.getUltimaFecha().toString());
                Label habitoLabel = new Label(habitoInfo);
                recomendacionesVBox.getChildren().add(habitoLabel);

                List<Recomendacion> recomendacionesPorHabito = recomendaciones.stream()
                        .filter(recomendacion -> recomendacion.getIdCategoria().getId().equals(habito.getIdActividad().getIdCategoria().getId()))
                        .collect(Collectors.toList());

                int index = 1;
                for (Recomendacion recomendacion : recomendacionesPorHabito) {
                    Label recomendacionLabel = new Label(index + "- " + recomendacion.getDescripcion());
                    recomendacionesVBox.getChildren().add(recomendacionLabel);
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}