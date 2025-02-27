package com.github.Hanselmito.view;

import com.github.Hanselmito.App;
import com.github.Hanselmito.controllers.RecomendacionController;
import com.github.Hanselmito.entities.Habito;
import com.github.Hanselmito.entities.Huella;
import com.github.Hanselmito.entities.Recomendacion;
import com.github.Hanselmito.entities.Usuario;
import com.github.Hanselmito.services.HabitoService;
import com.github.Hanselmito.services.HuellaService;
import com.github.Hanselmito.utils.PDFReportGenerator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController extends Controller implements Initializable {

    @FXML
    private ScrollPane huellaScrollPane;

    @FXML
    private ScrollPane habitoScrollPane;

    @FXML
    private ImageView avatarImage;

    @FXML
    private Label username;

    @FXML
    private MenuButton HuellaMenu;

    @FXML
    private MenuItem AnadirHuella;

    @FXML
    private MenuItem ActualizarHuella;

    @FXML
    private MenuItem BorrarHuella;

    @FXML
    private MenuButton HabitoMenu;

    @FXML
    private MenuItem anadirHabito;

    @FXML
    private MenuItem actualizarHabito;

    @FXML
    private MenuItem borrarHabito;

    @FXML
    private MenuButton funcionalidadMenu;

    @FXML
    private MenuItem calcularImpacto;

    @FXML
    private MenuItem recomendado;

    @FXML
    private MenuItem generarReporte;

    @FXML
    private MenuItem compararHuella;

    private HuellaService huellaService = new HuellaService();
    private HabitoService habitoService = new HabitoService();
    private Usuario currentUser;

    @FXML
    private AnchorPane huellaAnchorPane = new AnchorPane();

    @FXML
    private AnchorPane habitoAnchorPane = new AnchorPane();
    private Huella huellaSeleccionada;

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
            loadHabitos(currentUser);
        } else {
            throw new Exception("El input no es una instancia de Usuario");
        }
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        huellaAnchorPane.setId("huellaAnchorPane");
        habitoAnchorPane.setId("habitoAnchorPane");
        huellaScrollPane.setContent(huellaAnchorPane);
        habitoScrollPane.setContent(habitoAnchorPane);

        huellaScrollPane.setContent(huellaAnchorPane);
        habitoScrollPane.setContent(habitoAnchorPane);

        anadirHabito.setOnAction(event -> openManageHabitoWindow(currentUser, "Añadir"));
        actualizarHabito.setOnAction(event -> openManageHabitoWindow(currentUser, "Actualizar"));
        borrarHabito.setOnAction(event -> openManageHabitoWindow(currentUser, "Borrar"));

        calcularImpacto.setOnAction(event -> handleCalcularImpacto());
        recomendado.setOnAction(event -> handleRecomendado());
        generarReporte.setOnAction(event -> handleGenerarReporte());
        compararHuella.setOnAction(event -> handleCompararHuella());
    }

    private void handleCalcularImpacto() {
        try {
            if (huellaSeleccionada != null) {
                LocalDate fecha = LocalDate.now();
                String categoria = huellaSeleccionada.getIdActividad().getIdCategoria().getNombre();

                BigDecimal impactoDiario = huellaService.calcularImpactoDiario(currentUser, fecha, categoria);
                BigDecimal impactoSemanal = huellaService.calcularImpactoSemanal(currentUser, fecha, categoria);
                BigDecimal impactoMensual = huellaService.calcularImpactoMensual(currentUser, fecha, categoria);

                BigDecimal impactoCategoria = huellaService.calcularImpactoPorCategoria(currentUser, categoria, fecha.minusDays(7), fecha);

                showAlert("Impacto de Huella de Carbono",
                        "Impacto Diario: " + impactoDiario + "\n" +
                                "Impacto Semanal: " + impactoSemanal + "\n" +
                                "Impacto Mensual: " + impactoMensual + "\n" +
                                "Impacto de la Categoría (" + categoria + "): " + impactoCategoria);
            } else {
                showAlert("Error", "No se ha seleccionado ninguna huella.");
            }
        } catch (Exception e) {
            showAlert("Error", "No se pudo calcular el impacto de la huella de carbono: " + e.getMessage());
        }
    }

    @FXML
    private void handleRecomendado() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/github/Hanselmito/view/Recomendaciones.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            RecomendacionesController controller = fxmlLoader.getController();
            controller.onOpen(currentUser);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGenerarReporte() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                List<Huella> huellas = huellaService.findHuellasByUsuario(currentUser);
                List<Recomendacion> recomendaciones = new RecomendacionController().obtenerRecomendacionesPorHabitosPorCategoria(currentUser);
                List<Habito> habitos = new HabitoService().findHabitosByUsuario(currentUser);
                PDFReportGenerator pdfReportGenerator = new PDFReportGenerator();
                pdfReportGenerator.generateReport(file.getAbsolutePath(), huellas, recomendaciones, habitos);
                showAlert("Éxito", "Reporte generado correctamente.");
            } catch (Exception e) {
                showAlert("Error", "No se pudo generar el reporte: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleCompararHuella() {
        try {
            if (huellaSeleccionada != null) {
                String categoria = huellaSeleccionada.getIdActividad().getIdCategoria().getNombre();
                BigDecimal impactoUsuario = huellaService.calcularImpactoPorCategoria(currentUser, categoria, LocalDate.now().minusYears(1), LocalDate.now());
                BigDecimal mediaCategoria = huellaService.calcularMediaPorCategoria(categoria);

                showAlert("Comparación de Huella de Carbono",
                        "Categoría: " + categoria + "\n" +
                                "Impacto del Usuario: " + impactoUsuario + "\n" +
                                "Media de Otros Usuarios: " + mediaCategoria);
            } else {
                showAlert("Error", "No se ha seleccionado ninguna huella.");
            }
        } catch (Exception e) {
            showAlert("Error", "No se pudo comparar la huella de carbono: " + e.getMessage());
        }
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

    private void loadHuellas(Usuario usuario) {
        try {
            List<Huella> huellas = huellaService.findHuellasByUsuario(usuario);
            huellaAnchorPane.getChildren().clear();
            double yOffset = 10.0;
            for (Huella huella : huellas) {
                AnchorPane huellaPane = createHuellaPane(huella, yOffset);
                huellaAnchorPane.getChildren().add(huellaPane);
                yOffset += 100.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHabitos(Usuario usuario) {
        try {
            List<Habito> habitos = habitoService.findHabitosByUsuario(usuario);
            habitoAnchorPane.getChildren().clear();
            double yOffset = 10.0;
            for (Habito habito : habitos) {
                AnchorPane habitoPane = createHabitoPane(habito, yOffset);
                habitoAnchorPane.getChildren().add(habitoPane);
                yOffset += 100.0;
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
        App.currentController.changeScene(Scenes.MANAGERHUELLA, new Object[]{currentUser,"Actualizar"});
    }

    @FXML
    private void handleBorrarHuella() throws Exception {
        App.currentController.changeScene(Scenes.MANAGERHUELLA,new Object[]{currentUser,"Borrar"});
    }

    private AnchorPane createHuellaPane(Huella huella, double yOffset) throws Exception {
        AnchorPane pane = new AnchorPane();
        pane.setLayoutY(yOffset);
        pane.setPrefHeight(90.0);
        pane.setPrefWidth(huellaScrollPane.getPrefWidth() - 8);
        pane.setLayoutX(2.4);

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
        pane.setOnMouseClicked(event -> {
            try {
                if (pane.getStyle().contains("#bdff73")) {
                    pane.setStyle("");
                    huellaSeleccionada = null;
                } else {
                    huellaSeleccionada = huella;
                    pane.setStyle("-fx-background-color: #bdff73;");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return pane;
    }

    private AnchorPane createHabitoPane(Habito habito, double yOffset) throws Exception {
        AnchorPane pane = new AnchorPane();
        pane.setLayoutY(yOffset);
        pane.setPrefHeight(90.0);
        pane.setPrefWidth(habitoScrollPane.getPrefWidth() - 8);
        pane.setLayoutX(2.4);

        ImageView imageView = new ImageView(new Image(getClass().getResource("/com/github/Hanselmito/Icon/Huella.png").toExternalForm()));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setLayoutX(10);
        imageView.setLayoutY(10);

        Label nombreUsuario = new Label(habito.getIdUsuario().getNombre());
        nombreUsuario.setLayoutX(70);
        nombreUsuario.setLayoutY(10);

        String nombreActividad = habitoService.getNombreActividadById(habito.getIdActividad().getId());
        Label actividad = new Label("Actividad: " + nombreActividad);
        actividad.setLayoutX(70);
        actividad.setLayoutY(30);

        Label frecuenciaTipoFecha = new Label(habito.getFrecuencia() + " veces - " + habito.getTipo() + " - " + habito.getUltimaFecha());
        frecuenciaTipoFecha.setLayoutX(70);
        frecuenciaTipoFecha.setLayoutY(50);

        pane.getChildren().addAll(imageView, nombreUsuario, actividad, frecuenciaTipoFecha);
        pane.setOnMouseClicked(event -> {
            try {
                if (pane.getStyle().contains("#bdff73")) {
                    pane.setStyle("");
                } else {
                    handleSeleccionarHabito(habito);
                    pane.setStyle("-fx-background-color: #bdff73;");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return pane;
    }

    private void handleSeleccionarHabito(Habito habito) {
        if (habito != null) {
        } else {
            showAlert("Selección de hábito", "No se ha seleccionado ningún hábito");
        }
    }

    public void openManageHabitoWindow(Usuario usuario, String action) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/github/Hanselmito/view/ManageHabito.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ManageHabitoController controller = fxmlLoader.getController();
            controller.onOpen(new Object[]{usuario, action});
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GoMenu(MouseEvent event)throws Exception{
        App.currentController.changeScene(Scenes.MENU, currentUser);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}