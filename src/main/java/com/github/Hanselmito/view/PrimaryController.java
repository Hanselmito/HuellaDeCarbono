package com.github.Hanselmito.view;

import java.io.IOException;

import com.github.Hanselmito.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
