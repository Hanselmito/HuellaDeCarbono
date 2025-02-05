package com.github.Hanselmito;

import com.github.Hanselmito.view.AppController;
import com.github.Hanselmito.view.Scenes;
import com.github.Hanselmito.view.View;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        App.stage = primaryStage;
        View view = AppController.loadFXML(Scenes.ROOT);

        scene = new Scene(view.scene);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        currentController = (AppController) view.controller;
        currentController.onOpen(null);
    }
}