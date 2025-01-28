package com.github.Hanselmito;

import com.github.Hanselmito.view.AppController;
import com.github.Hanselmito.view.Scenes;
import com.github.Hanselmito.view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;

    @Override
    public void start(Stage stage) throws Exception {
        View view = AppController.loadFXML(Scenes.ROOT);
        scene = new Scene(view.scene, 652, 403);
        currentController = (AppController) view.controller;
        currentController.onOpen(null);
        stage.setScene(scene);
        stage.show();
    }
}