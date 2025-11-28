package org.example.tp2_s3;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFx extends Application {
    public static final double WIDTH = 900, HEIGHT = 580;
    private Stage stage;
    public boolean enPause = false;// todo potentiellement a enlever

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();
        Partie partie = new Partie();



        var timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();
            @Override
            public void handle(long temps) {
                double deltaTemps = (temps - dernierTemps) * 1e-9;
                partie.update(deltaTemps);
                partie.draw(context);
                dernierTemps = temps;
            }
        };
        timer.start();


        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                // Ferme JavaFX
                Platform.exit();
            } else {
                Input.setKeyPressed(e.getCode(), true);
            }
            //todo est-ce qu'il faut mettre en pause?????
        });
        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Jeu Camelot");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    public void sceneAccueil() {
        //todo changer le stage
    }

    public void sceneJeux() {
        //todo changer le stage
    }


}