package com.github.Hanselmito.view;

import com.github.Hanselmito.App;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.services.HuellaService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController extends Controller implements Initializable {

    @FXML
    private ScrollPane huellaScrollPane;

    @FXML
    private ImageView avatarImage;

    @FXML
    private Label username;

    @FXML
    private MenuButton filterByMenu;

    @FXML
    private MenuButton HuellaMenu;

    @FXML
    private MenuItem AnadirHuella;

    @FXML
    private MenuItem ActualizarHuella;

    @FXML
    private MenuItem BorrarHuella;

    private HuellaService huellaService = new HuellaService();
    private Usuario currentUser;

    @FXML
    private AnchorPane huellaAnchorPane = new AnchorPane();

    @Override
    public void onOpen(Object input) throws Exception {
        if (input instanceof Usuario) {
            currentUser = (Usuario) input;
            username.setText(currentUser.getNombre());
            try {
                URL avatarUrl = getClass().getResource("/com/github/Hanselmito/Icon/Avatar.png");
                if (avatarUrl != null) {
                    avatarImage.setImage(new Image(avatarUrl.toString()));
                } else {
                    throw new Exception("El archivo de imagen del avatar no se encuentra");
                }
            } catch (Exception e) {
                throw new Exception("Error al cargar la imagen del avatar", e);
            }
            loadHuellas(currentUser);
        } else {
            throw new Exception("El input no es una instancia de Usuario");
        }
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        huellaScrollPane.setContent(huellaAnchorPane);
    }

    @FXML
    private void handleAvatarClick(MouseEvent event) throws Exception {
        App.currentController.changeScene(Scenes.SETTINGUSER, null);
    }

    private void loadHuellas(Usuario usuario) {
        try {
            List<Huella> huellas = huellaService.findHuellasByUsuario(usuario);
            huellaAnchorPane.getChildren().clear();
            double yOffset = 10.0;
            for (Huella huella : huellas) {
                AnchorPane huellaPane = createHuellaPane(huella, yOffset);
                huellaAnchorPane.getChildren().add(huellaPane);
                yOffset += 100.0; // Ajusta el valor según el tamaño del panel de huella
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAnadirHuella() throws Exception {
        App.currentController.changeScene(Scenes.MANAGERHUELLA, new Object[]{currentUser,"Añadir"});
    }

    @FXML
    private void handleActualizarHuella() throws Exception {
        App.currentController.changeScene(Scenes.MANAGERHUELLA, "Actualizar");
    }

    @FXML
    private void handleBorrarHuella() throws Exception {
        App.currentController.changeScene(Scenes.MANAGERHUELLA, "Borrar");
    }

    private AnchorPane createHuellaPane(Huella huella, double yOffset) throws Exception {
        AnchorPane pane = new AnchorPane();
        pane.setLayoutY(yOffset);
        pane.setPrefHeight(90.0);
        pane.setPrefWidth(huellaScrollPane.getPrefWidth() - 20);

        ImageView imageView = new ImageView(new Image(getClass().getResource("/com/github/Hanselmito/Icon/Huella.png").toExternalForm()));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setLayoutX(10);
        imageView.setLayoutY(10);

        Label nombreUsuario = new Label(huella.getIdUsuario().getNombre());
        nombreUsuario.setLayoutX(70);
        nombreUsuario.setLayoutY(10);

        String nombreActividad = huellaService.getNombreActividadById(huella.getIdActividad().getId());
        Label actividad = new Label("Actividad: " + nombreActividad);
        actividad.setLayoutX(70);
        actividad.setLayoutY(30);

        Label valorUnidadFecha = new Label(huella.getValor() + " " + huella.getUnidad() + " - " + huella.getFecha());
        valorUnidadFecha.setLayoutX(70);
        valorUnidadFecha.setLayoutY(50);

        pane.getChildren().addAll(imageView, nombreUsuario, actividad, valorUnidadFecha);
        return pane;
    }
}