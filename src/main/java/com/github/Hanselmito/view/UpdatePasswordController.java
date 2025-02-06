package com.github.Hanselmito.view;

import com.github.Hanselmito.App;
import com.github.Hanselmito.controllers.UsuarioController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePasswordController extends Controller implements Initializable {
    @FXML
    private TextField TextEmail;

    @FXML
    private PasswordField TextNewPassword;

    @FXML
    private PasswordField TextConfirmPassword;

    @FXML
    private Button Confirm;

    @FXML
    private Label errorEmail;

    @FXML
    private Label errorP;

    @FXML
    private Label errorP2;

    @FXML
    private Label SignIn;

    private UsuarioController usuarioController = new UsuarioController();

    @Override
    public void onOpen(Object input) throws Exception {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Confirm.setOnAction(event -> {
            try {
                handleUpdatePassword();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        TextConfirmPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    handleUpdatePassword();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        SignIn.setOnMouseClicked(event -> {
            try {
                App.currentController.changeScene(Scenes.LOGIN, null);
                Stage stage = (Stage) SignIn.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleUpdatePassword() throws Exception {
        String email = TextEmail.getText();
        String newPassword = TextNewPassword.getText();
        String confirmPassword = TextConfirmPassword.getText();

        if (!newPassword.equals(confirmPassword)) {
            errorP.setText("Las contraseñas no coinciden");
            errorP.setVisible(true);
            errorP2.setText("Las contraseñas no coinciden");
            errorP2.setVisible(true);
            return;
        }

        try {
            usuarioController.updatePassword(email, newPassword);
            Stage stage = (Stage) Confirm.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            errorEmail.setText("Email no encontrado en la base de datos");
            errorEmail.setVisible(true);
        }
    }
}