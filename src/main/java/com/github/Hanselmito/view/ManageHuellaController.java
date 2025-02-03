package com.github.Hanselmito.view;

import com.github.Hanselmito.App;
import com.github.Hanselmito.controllers.HuellaController;
import com.github.Hanselmito.entities.Actividad;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.services.ActividadService;
import com.github.Hanselmito.services.HuellaService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManageHuellaController extends Controller implements Initializable {

    @FXML
    private ImageView avatarImage;

    @FXML
    private Label username;

    @FXML
    private ImageView Home;

    @FXML
    private Label labelHome;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anadirPane;

    @FXML
    private AnchorPane actualizarPane;

    @FXML
    private AnchorPane borrarPane;

    @FXML
    private TextField anadirUsuarioTextField;

    @FXML
    private ChoiceBox<Actividad> anadirActividadChoiceBox;

    @FXML
    private TextField anadirValorTextField;

    @FXML
    private ChoiceBox<String> anadirUnidadChoiceBox;

    @FXML
    private TextField actualizarUsuarioTextField;

    @FXML
    private ChoiceBox<Actividad> actualizarActividadChoiceBox;

    @FXML
    private TextField actualizarValorTextField;

    @FXML
    private ChoiceBox<String> actualizarUnidadChoiceBox;

    @FXML
    private TableView<Huella> huellaTableView;

    @FXML
    private TableColumn<Huella, String> usuarioColumn;

    @FXML
    private TableColumn<Huella, String> actividadColumn;

    @FXML
    private TableColumn<Huella, String> valorColumn;

    @FXML
    private TableColumn<Huella, String> unidadColumn;

    @FXML
    private TableColumn<Huella, String> fechaColumn;

    @FXML
    private TableView<Huella> UpdateHuellaTableView;

    @FXML
    private TableColumn<Huella, String> UpdateUsuarioColumn;

    @FXML
    private TableColumn<Huella, String> UpdateActividadColumn;

    @FXML
    private TableColumn<Huella, String> UpdateValorColumn;

    @FXML
    private TableColumn<Huella, String> UpdateUnidadColumn;

    @FXML
    private TableColumn<Huella, String> UpdateFechaColumn;

    private ActividadService actividadService = new ActividadService();
    private HuellaService huellaService = new HuellaService();
    private HuellaController huellaController = new HuellaController();
    private Usuario currentUser;

    @Override
    public void onOpen(Object input) throws Exception {
        if (input instanceof Object[]) {
            Object[] inputs = (Object[]) input;
            if (inputs.length == 2 && inputs[0] instanceof Usuario && inputs[1] instanceof String) {
                currentUser = (Usuario) inputs[0];
                String action = (String) inputs[1];
                username.setText(currentUser.getNombre());
                switch (action) {
                    case "Añadir":
                        showPane(anadirPane);
                        loadUsuario(currentUser);
                        loadActividades();
                        break;
                    case "Actualizar":
                        showPane(actualizarPane);
                        loadUsuario(currentUser);
                        loadActividades();
                        loadNuevasHuellas(currentUser);
                        break;
                    case "Borrar":
                        showPane(borrarPane);
                        loadHuellas(currentUser);
                        break;
                    default:
                        throw new Exception("Acción no reconocida: " + action);
                }
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
            } else {
                throw new Exception("El input no es válido");
            }
        } else {
            throw new Exception("El input no es una instancia de Object[]");
        }
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar las opciones de unidad
        ObservableList<String> unidades = FXCollections.observableArrayList("Km", "KWh", "Kg", "Kg", "m³");
        anadirUnidadChoiceBox.setItems(unidades);
        actualizarUnidadChoiceBox.setItems(unidades);

        //Deshabilitar los ChoiceBox de unidad
        anadirUnidadChoiceBox.setDisable(true);
        actualizarUnidadChoiceBox.setDisable(true);

        // Configurar el ChangeListener para anadirActividadChoiceBox
        anadirActividadChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                anadirUnidadChoiceBox.setValue(getUnidadPorActividad(newValue));
            }
        });

        // Configurar el ChangeListener para actualizarActividadChoiceBox
        actualizarActividadChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarUnidadChoiceBox.setValue(getUnidadPorActividad(newValue));
            }
        });

        // Configurar las columnas de las tablas Borrar y actualizar
        usuarioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUsuario().getNombre()));
        actividadColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdActividad().getNombre()));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        unidadColumn.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        UpdateUsuarioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUsuario().getNombre()));
        UpdateActividadColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdActividad().getNombre()));
        UpdateValorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        UpdateUnidadColumn.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        UpdateFechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        // Configurar TextFormatter para los campos de Valor
        setTextFormatter(anadirValorTextField);
        setTextFormatter(actualizarValorTextField);
    }

    @FXML
    private void handleAvatarClick(MouseEvent event) throws Exception {
        openSettingUsuarioWindow(currentUser);
    }

    private void openSettingUsuarioWindow(Usuario usuario) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/github/Hanselmito/view/SettingUsuario.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        SettingUsuarioController controller = fxmlLoader.getController();
        controller.onOpen(usuario);
        stage.showAndWait();
    }

    private void setTextFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d{0,10}([\\.]\\d{0,2})?")) {
                return change;
            }
            return null;
        }));
    }

    private void showPane(AnchorPane pane) {
        anadirPane.setVisible(false);
        actualizarPane.setVisible(false);
        borrarPane.setVisible(false);
        pane.setVisible(true);
    }

    private void loadUsuario(Usuario usuario) {
        anadirUsuarioTextField.setText(usuario.getNombre());
        actualizarUsuarioTextField.setText(usuario.getNombre());
    }

    private void loadActividades() {
        try {
            List<Actividad> actividades = actividadService.findAll();
            ObservableList<Actividad> actividadList = FXCollections.observableArrayList(actividades);
            anadirActividadChoiceBox.setItems(actividadList);
            actualizarActividadChoiceBox.setItems(actividadList);

            anadirActividadChoiceBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Actividad actividad) {
                    return actividad.getNombre();
                }

                @Override
                public Actividad fromString(String string) {
                    return actividadList.stream().filter(a -> a.getNombre().equals(string)).findFirst().orElse(null);
                }
            });

            actualizarActividadChoiceBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Actividad actividad) {
                    return actividad.getNombre();
                }

                @Override
                public Actividad fromString(String string) {
                    return actividadList.stream().filter(a -> a.getNombre().equals(string)).findFirst().orElse(null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHuellas(Usuario usuario) {
        try {
            List<Huella> huellas = huellaService.findHuellasByUsuario(usuario);
            ObservableList<Huella> huellaList = FXCollections.observableArrayList(huellas);
            huellaTableView.setItems(huellaList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadNuevasHuellas(Usuario usuario) {
        try {
            List<Huella> huellas = huellaService.findHuellasByUsuario(usuario);
            ObservableList<Huella> huellaList = FXCollections.observableArrayList(huellas);
            UpdateHuellaTableView.setItems(huellaList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddHuella() {
        try {
            Actividad actividad = anadirActividadChoiceBox.getValue();
            BigDecimal valor = new BigDecimal(anadirValorTextField.getText());
            String unidad = anadirUnidadChoiceBox.getValue();
            LocalDate fecha = LocalDate.now();

            Huella huella = new Huella();
            huella.setIdUsuario(currentUser);
            huella.setIdActividad(actividad);
            huella.setValor(valor);
            huella.setUnidad(unidad);
            huella.setFecha(fecha);

            huellaController.addHuella(huella);
            showAlert("Registro exitoso", "Huella registrada correctamente.");
            loadHuellas(currentUser);
            loadNuevasHuellas(currentUser);
            App.currentController.changeScene(Scenes.MENU, currentUser);
        } catch (Exception e) {
            showAlert("Error de registro", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateHuella() {
        try {
            Huella huella = UpdateHuellaTableView.getSelectionModel().getSelectedItem();
            if (huella != null) {
                huella.setIdUsuario(currentUser);
                huella.setIdActividad(actualizarActividadChoiceBox.getValue());
                huella.setValor(new BigDecimal(actualizarValorTextField.getText()));
                huella.setUnidad(actualizarUnidadChoiceBox.getValue());

                huellaController.updateHuella(huella);
                showAlert("Actualización exitosa", "Huella actualizada correctamente.");
                loadHuellas(currentUser);
                loadNuevasHuellas(currentUser);
                App.currentController.changeScene(Scenes.MENU, currentUser);
            } else {
                showAlert("Error de actualización", "Seleccione una huella para actualizar.");
            }
        } catch (Exception e) {
            showAlert("Error de actualización", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteHuella() {
        try {
            Huella huella = huellaTableView.getSelectionModel().getSelectedItem();
            if (huella != null) {
                huellaController.deleteHuella(huella);
                showAlert("Eliminación exitosa", "Huella eliminada correctamente.");
                loadHuellas(huella.getIdUsuario());
                loadNuevasHuellas(huella.getIdUsuario());
                App.currentController.changeScene(Scenes.MENU, currentUser);
            } else {
                showAlert("Error de eliminación", "Seleccione una huella para eliminar.");
            }
        } catch (Exception e) {
            showAlert("Error de eliminación", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    private String getUnidadPorActividad(Actividad actividad) {
        return actividad.getIdCategoria().getUnidad();
    }

    @FXML
    private void GoMenu(MouseEvent event)throws Exception{
        App.currentController.changeScene(Scenes.MENU, currentUser);
    }
}