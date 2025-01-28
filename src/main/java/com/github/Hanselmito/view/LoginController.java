package com.github.Hanselmito.view;

import com.github.Hanselmito.App;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void switchToSecondary() throws Exception {
        App.currentController.changeScene(Scenes.SECUNDARY,null);
    }
}
