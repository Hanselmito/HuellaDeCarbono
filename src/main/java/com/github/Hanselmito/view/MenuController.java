package com.github.Hanselmito.view;

import com.github.Hanselmito.App;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.services.HuellaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController extends Controller implements Initializable {

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

    @FXML
    private ListView<Huella> huellaListView;

    private HuellaService huellaService = new HuellaService();
    private Usuario currentUser;

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
        loadActividades();
        huellaListView.setCellFactory(new Callback<ListView<Huella>, ListCell<Huella>>() {
            @Override
            public ListCell<Huella> call(ListView<Huella> listView) {
                return new HuellaListCell();
            }
        });
    }

    @FXML
    private void handleAvatarClick(MouseEvent event) throws Exception {
        App.currentController.changeScene(Scenes.SETTINGUSER, null);
    }

    private void loadActividades() {

    }

    private void loadHuellas(Usuario usuario) {
        try {
            List<Huella> huellas = huellaService.findHuellasByUsuario(usuario);
            huellaListView.setItems(FXCollections.observableArrayList(huellas));
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

    private class HuellaListCell extends ListCell<Huella> {
        @Override
        protected void updateItem(Huella huella, boolean empty) {
            super.updateItem(huella, empty);
            if (empty || huella == null) {
                setText(null);
                setGraphic(null);
            } else {
                try {
                    ImageView imageView = new ImageView(new Image(getClass().getResource("/com/github/Hanselmito/Icon/Huella.png").toExternalForm()));
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    setText(huella.getIdUsuario().getNombre() + " - " + huella.getValor() + " " + huella.getUnidad() + " - " + huella.getFecha());
                    setGraphic(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                    setText("Error al cargar la imagen");
                    setGraphic(null);
                }
            }
        }
    }
}