package com.github.Hanselmito.view;

import com.github.Hanselmito.App;
import com.github.Hanselmito.controllers.UsuarioController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private Label errorP;

    @FXML
    private Label errorUE;

    @FXML
    private TextField TextUser;

    @FXML
    private TextField TextPassword;

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
    }

    private void handleLogin() throws Exception {
        String identifier = TextUser.getText();
        String contrasena = TextPassword.getText();
        if (usuarioController.comprobarCredenciales(identifier, contrasena)) {
            App.currentController.changeScene(Scenes.REGISTER, null);
        } else {
            if (!usuarioController.comprobarCredenciales(identifier, "")) {
                errorUE.setText("Nombre de Usuario o Email no válido");
                errorUE.setVisible(true);
            } else {
                errorP.setText("Contraseña incorrecta");
                errorP.setVisible(true);
            }
        }
    }

    @Override
    public void onOpen(Object input) throws Exception {
        // Implementación específica de LoginController
    }

    @Override
    public void onClose(Object output) {
        // Implementación específica de LoginController
    }
}