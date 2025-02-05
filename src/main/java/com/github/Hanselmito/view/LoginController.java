package com.github.Hanselmito.view;

import com.github.Hanselmito.App;
import com.github.Hanselmito.controllers.UsuarioController;
import com.github.Hanselmito.entities.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private Label errorP;

    @FXML
    private Label errorUE;

    @FXML
    private Label SignUp;

    @FXML
    private Label Forgotten;

    @FXML
    private TextField TextUser;

    @FXML
    private PasswordField TextPassword;

    @FXML
    private Button Login;

    private UsuarioController usuarioController = new UsuarioController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Login.setOnAction(event -> {
            try {
                handleLogin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        SignUp.setOnMouseClicked(event -> {
            try {
                handleRegister();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        Forgotten.setOnMouseClicked(event ->{
            try {
                handleForgotten();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleLogin() throws Exception {
        String identifier = TextUser.getText();
        String contrasena = TextPassword.getText();
        System.out.println("Intentando iniciar sesión con usuario: " + identifier);
        try {
            Usuario usuario = usuarioController.login(identifier, contrasena);
            if (usuario != null) {
                System.out.println("Inicio de sesión exitoso para el usuario: " + usuario.getNombre());
                App.currentController.changeScene(Scenes.MENU, usuario);
            } else {
                if (!usuarioController.comprobarCredenciales(identifier, "")) {
                    errorUE.setText("Nombre de Usuario o Email no válido");
                    errorUE.setVisible(true);
                } else {
                    errorP.setText("Contraseña incorrecta");
                    errorP.setVisible(true);
                }
            }
        } catch (Exception e) {
            errorUE.setText("Nombre de Usuario o Email no válido");
            errorUE.setVisible(true);
            e.printStackTrace();
        }
    }

    private void handleRegister() throws Exception{
        App.currentController.changeScene(Scenes.REGISTER, null);
    }

    private void handleForgotten() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/github/Hanselmito/view/UpdatePassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @Override
    public void onOpen(Object input) throws Exception {

    }

    @Override
    public void onClose(Object output) {

    }
}