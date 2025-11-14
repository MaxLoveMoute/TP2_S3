package org.example.tp2_s3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFx extends Application {
    public static final double WIDTH = 1600, HEIGHT = 900;

    @Override
    public void start(Stage stage) throws IOException {
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }



    public void sceneAccueil () {
        //todo
    }

    public void sceneJeux () {
        //todo
    }






}