package com.github.Hanselmito.view;

import com.github.Hanselmito.App;
import com.github.Hanselmito.entities.Actividad;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.services.ActividadService;
import com.github.Hanselmito.services.HuellaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManageHuellaController extends Controller implements Initializable {

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
    private TextField anadirUnidadTextField;

    @FXML
    private TextField actualizarUsuarioTextField;

    @FXML
    private ChoiceBox<Actividad> actualizarActividadChoiceBox;

    @FXML
    private TextField actualizarValorTextField;

    @FXML
    private TextField actualizarUnidadTextField;

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

    private ActividadService actividadService = new ActividadService();
    private HuellaService huellaService = new HuellaService();
    private Usuario currentUser;

    @Override
    public void onOpen(Object input) throws Exception {
        if (input instanceof Object[]) {
            Object[] inputs = (Object[]) input;
            if (inputs.length == 2 && inputs[0] instanceof Usuario && inputs[1] instanceof String) {
                currentUser = (Usuario) inputs[0];
                String action = (String) inputs[1];
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
                        break;
                    case "Borrar":
                        showPane(borrarPane);
                        break;
                    default:
                        throw new Exception("Acción no reconocida: " + action);
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
        usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        actividadColumn.setCellValueFactory(new PropertyValueFactory<>("idActividad"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        unidadColumn.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        // Configurar TextFormatter para los campos de Valor
        setTextFormatter(anadirValorTextField);
        setTextFormatter(actualizarValorTextField);
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

            anadirActividadChoiceBox.setConverter(new StringConverter<Actividad>() {
                @Override
                public String toString(Actividad actividad) {
                    return actividad.getNombre();
                }

                @Override
                public Actividad fromString(String string) {
                    return actividadList.stream().filter(a -> a.getNombre().equals(string)).findFirst().orElse(null);
                }
            });

            actualizarActividadChoiceBox.setConverter(new StringConverter<Actividad>() {
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

    @FXML
    private void handleAddHuella() {
        try {
            Actividad actividad = anadirActividadChoiceBox.getValue();
            BigDecimal valor = new BigDecimal(anadirValorTextField.getText());
            String unidad = anadirUnidadTextField.getText();
            LocalDate fecha = LocalDate.now();

            Huella huella = new Huella();
            huella.setIdUsuario(currentUser);
            huella.setIdActividad(actividad);
            huella.setValor(valor);
            huella.setUnidad(unidad);
            huella.setFecha(fecha);

            huellaService.addHuella(huella);
            showAlert("Registro exitoso", "Huella registrada correctamente.");
            loadHuellas(currentUser);
        } catch (Exception e) {
            showAlert("Error de registro", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateHuella() {
        try {
            Huella huella = huellaTableView.getSelectionModel().getSelectedItem();
            if (huella != null) {
                huella.setIdUsuario(currentUser);
                huella.setIdActividad(actualizarActividadChoiceBox.getValue());
                huella.setValor(new BigDecimal(actualizarValorTextField.getText()));
                huella.setUnidad(actualizarUnidadTextField.getText());

                huellaService.updateHuella(huella);
                showAlert("Actualización exitosa", "Huella actualizada correctamente.");
                loadHuellas(currentUser);
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
                huellaService.deleteHuella(huella);
                showAlert("Eliminación exitosa", "Huella eliminada correctamente.");
                loadHuellas(huella.getIdUsuario());
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
}