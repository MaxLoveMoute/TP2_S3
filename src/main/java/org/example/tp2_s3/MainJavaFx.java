package org.example.tp2_s3;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainJavaFx extends Application {
    public static final int WIDTH = 900, HEIGHT = 580;
    private Stage stage;
    private int niveauRendu = 1;
    private int journauxConserves = 0;
    private int argentCollecte = 0;


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        primaryStage.setScene(sceneAccueil());
        primaryStage.setTitle("Camelot à vélot");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }



    public Scene sceneAccueil() {
        var root = new StackPane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, WIDTH, HEIGHT);
        context.setFill(Color.GREEN);
        context.setFont(new Font("Arial", 40));
        context.fillText("Niveau " + niveauRendu, (WIDTH/2) - 75, HEIGHT/2);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            stage.setScene(sceneJeu());
        });
        pause.play();

        return scene;
    }



    public Scene sceneJeu() {
        Input.reset();
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        Partie partie = new Partie(niveauRendu, journauxConserves);

        var timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();
            @Override
            public void handle(long temps) {
                double deltaTemps = (temps - dernierTemps) * 1e-9;
                partie.update(deltaTemps);
                partie.draw(context);
                dernierTemps = temps;
                if (partie.isTermine()) {
                    niveauRendu++;
                    journauxConserves = partie.journauxRestants();
                    Input.reset();
                    this.stop();  // arrête l’animation
                    stage.setScene(sceneAccueil());
                }

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
        });
        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });

        return scene;
    }



    public Scene sceneScore() {
        var root = new StackPane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        context.setFont(new Font("Arial", 40));

        context.setFill(Color.RED);
        context.fillText("Rupture de stock", (WIDTH/2) - 150, (HEIGHT/2) - 100);

        context.setFill(Color.GREEN);
        context.fillText( "Argent collecté " + argentCollecte + " $" , (WIDTH/2) - 160, (HEIGHT/2) + 100);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            niveauRendu = 1;
            argentCollecte = 0;
            stage.setScene(sceneAccueil());
        });
        pause.play();

        return scene;
    }


}