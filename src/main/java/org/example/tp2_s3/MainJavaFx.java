package org.example.tp2_s3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFx extends Application {
    public static final double WIDTH = 1600, HEIGHT = 900;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();





        primaryStage.setScene(scene);
        primaryStage.setTitle("Animations 5");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }



    public void sceneAccueil () {
        //todo changer le stage
    }

    public void sceneJeux () {
        //todo changer le stage
    }






}