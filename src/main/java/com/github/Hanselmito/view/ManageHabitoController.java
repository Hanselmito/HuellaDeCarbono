package com.github.Hanselmito.view;

import javafx.beans.property.SimpleStringProperty;
import com.github.Hanselmito.entities.Actividad;
import com.github.Hanselmito.entities.Habito;
import com.github.Hanselmito.entities.HabitoId;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.entities.Enums.TipoEnum;
import com.github.Hanselmito.services.ActividadService;
import com.github.Hanselmito.services.HabitoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManageHabitoController extends Controller implements Initializable {

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
    private TextField anadirFrecuenciaTextField;

    @FXML
    private ChoiceBox<TipoEnum> anadirTipoChoiceBox;

    @FXML
    private TextField actualizarUsuarioTextField;

    @FXML
    private ChoiceBox<Actividad> actualizarActividadChoiceBox;

    @FXML
    private TextField actualizarFrecuenciaTextField;

    @FXML
    private ChoiceBox<TipoEnum> actualizarTipoChoiceBox;

    @FXML
    private DatePicker actualizarUltimaFechaDatePicker;

    @FXML
    private TableView<Habito> UpdateHabitoTableView;

    @FXML
    private TableColumn<Habito, String> UpdateUsuarioColumn;

    @FXML
    private TableColumn<Habito, String> UpdateActividadColumn;

    @FXML
    private TableColumn<Habito, Integer> UpdateFrecuenciaColumn;

    @FXML
    private TableColumn<Habito, TipoEnum> UpdateTipoColumn;

    @FXML
    private TableColumn<Habito, LocalDate> UpdateUltimaFechaColumn;

    @FXML
    private TableView<Habito> habitoTableView;

    @FXML
    private TableColumn<Habito, String> usuarioColumn;

    @FXML
    private TableColumn<Habito, String> actividadColumn;

    @FXML
    private TableColumn<Habito, Integer> frecuenciaColumn;

    @FXML
    private TableColumn<Habito, TipoEnum> tipoColumn;

    @FXML
    private TableColumn<Habito, LocalDate> ultimaFechaColumn;

    private ActividadService actividadService = new ActividadService();
    private HabitoService habitoService = new HabitoService();
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
                        loadHabitos(currentUser);
                        break;
                    case "Borrar":
                        showPane(borrarPane);
                        loadHabitos(currentUser);
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
        ObservableList<TipoEnum> tipos = FXCollections.observableArrayList(TipoEnum.values());
        anadirTipoChoiceBox.setItems(tipos);
        actualizarTipoChoiceBox.setItems(tipos);

        usuarioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUsuario().getNombre()));
        actividadColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdActividad().getNombre()));
        frecuenciaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuencia"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        ultimaFechaColumn.setCellValueFactory(new PropertyValueFactory<>("ultimaFecha"));

        UpdateUsuarioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdUsuario().getNombre()));
        UpdateActividadColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdActividad().getNombre()));
        UpdateFrecuenciaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuencia"));
        UpdateTipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        UpdateUltimaFechaColumn.setCellValueFactory(new PropertyValueFactory<>("ultimaFecha"));

        setTextFormatter(anadirFrecuenciaTextField);
        setTextFormatter(actualizarFrecuenciaTextField);
    }

    private void setTextFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d{0,10}")) {
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

    private void loadHabitos(Usuario usuario) {
        try {
            List<Habito> habitos = habitoService.findHabitosByUsuario(usuario);
            ObservableList<Habito> habitoList = FXCollections.observableArrayList(habitos);
            habitoTableView.setItems(habitoList);
            UpdateHabitoTableView.setItems(habitoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddHabito() {
        try {
            Actividad actividad = anadirActividadChoiceBox.getValue();
            int frecuencia = Integer.parseInt(anadirFrecuenciaTextField.getText());
            TipoEnum tipo = anadirTipoChoiceBox.getValue();
            LocalDate ultimaFecha = LocalDate.now();

            HabitoId habitoId = new HabitoId();
            habitoId.setIdUsuario(currentUser.getId());
            habitoId.setIdActividad(actividad.getId());

            Habito habito = new Habito();
            habito.setId(habitoId);
            habito.setIdUsuario(currentUser);
            habito.setIdActividad(actividad);
            habito.setFrecuencia(frecuencia);
            habito.setTipo(tipo);
            habito.setUltimaFecha(ultimaFecha);

            habitoService.addHabito(habito);
            showAlert("Registro exitoso", "Hábito registrado correctamente.");
            loadHabitos(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error de registro", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateHabito() {
        try {
            Habito habito = UpdateHabitoTableView.getSelectionModel().getSelectedItem();
            if (habito != null) {
                habito.setFrecuencia(Integer.parseInt(actualizarFrecuenciaTextField.getText()));
                habito.setTipo(TipoEnum.fromString(actualizarTipoChoiceBox.getValue().toString()));
                habito.setUltimaFecha(actualizarUltimaFechaDatePicker.getValue());
                habitoService.updateHabito(habito);
                showAlert("Actualización exitosa", "Hábito actualizado correctamente.");
                loadHabitos(currentUser);
            } else {
                showAlert("Error de actualización", "Seleccione un hábito para actualizar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error de actualización", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteHabito() {
        try {
            Habito habito = habitoTableView.getSelectionModel().getSelectedItem();
            if (habito != null) {
                habitoService.deleteHabito(habito);
                showAlert("Eliminación exitosa", "Hábito eliminado correctamente.");
                loadHabitos(currentUser);
            } else {
                showAlert("Error de eliminación", "Seleccione un hábito para eliminar.");
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