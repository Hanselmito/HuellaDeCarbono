package com.github.Hanselmito;

import com.github.Hanselmito.view.AppController;
import com.github.Hanselmito.view.Scenes;
import com.github.Hanselmito.view.View;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;

    @Override
    public void start(Stage stage) throws Exception {
        View view = AppController.loadFXML(Scenes.ROOT);

        // Obtener las dimensiones de la pantalla del dispositivo
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        scene = new Scene(view.scene, screenBounds.getWidth(), screenBounds.getHeight());

        currentController = (AppController) view.controller;
        currentController.onOpen(null);
        stage.setScene(scene);
        stage.show();
    }
}