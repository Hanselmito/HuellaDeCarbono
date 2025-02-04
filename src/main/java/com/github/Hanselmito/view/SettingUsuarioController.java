package com.github.Hanselmito.view;

import com.github.Hanselmito.controllers.UsuarioController;
import com.github.Hanselmito.entities.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingUsuarioController extends Controller implements Initializable {
    @FXML
    private TextField TextName;

    @FXML
    private TextField TextEmail;

    @FXML
    private PasswordField TextPassword;

    @FXML
    private Button UpdateButton;

    private UsuarioController usuarioController = new UsuarioController();
    private Usuario currentUser;

    @Override
    public void onOpen(Object input) throws Exception {
        if (input instanceof Usuario) {
            currentUser = (Usuario) input;
            TextName.setText(currentUser.getNombre());
            TextEmail.setText(currentUser.getEmail());
        } else {
            throw new Exception("El input no es una instancia de Usuario");
        }
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateButton.setOnAction(event -> {
            try {
                handleUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleUpdate() throws Exception {
        String nombre = TextName.getText();
        String email = TextEmail.getText();
        String contrasena = TextPassword.getText();

        currentUser.setNombre(nombre);
        currentUser.setEmail(email);
        if (!contrasena.isEmpty()) {
            currentUser.setContrasena(contrasena);
        }

        try {
            usuarioController.updateUsuario(currentUser);
            Stage stage = (Stage) UpdateButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}