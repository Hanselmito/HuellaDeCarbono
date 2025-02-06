package com.github.Hanselmito.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.github.Hanselmito.App;
import com.github.Hanselmito.controllers.UsuarioController;
import com.github.Hanselmito.entities.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

public class RegisterController extends Controller implements Initializable {

    @FXML
    private TextField TextName;

    @FXML
    private TextField TextEmail;

    @FXML
    private PasswordField TextPassword;

    @FXML
    private Label errorP;

    @FXML
    private Label SignIn;

    @FXML
    private Button Register;

    private UsuarioController usuarioController = new UsuarioController();

    @Override
    public void onOpen(Object input) throws Exception {
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Register.setOnAction(event -> {
            try {
                handleRegister();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        TextPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    handleRegister();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SignIn.setOnMouseClicked(event -> {
            try {
                handleLogin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleRegister() throws Exception {
        String nombre = TextName.getText();
        String email = TextEmail.getText();
        String contrasena = TextPassword.getText();

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasena(contrasena);

        try {
            usuarioController.addUsuario(usuario);
            App.currentController.changeScene(Scenes.LOGIN, null);
        } catch (Exception e) {
            errorP.setText(e.getMessage());
            errorP.setVisible(true);
            e.printStackTrace();
        }
    }

    private void handleLogin() throws Exception{
        App.currentController.changeScene(Scenes.LOGIN, null);
    }
}