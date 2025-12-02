package org.example.tp2_s3;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Classe principale de l'application "Camelot à vélot".
 *
 * <p>
 * Cette classe gère le cycle complet du jeu : écran d'accueil, scène de jeu,
 * et écran de score. Elle utilise JavaFX pour le rendu graphique et les entrées
 * clavier, ainsi que  AnimationTimer pour gérer la boucle principale du jeu.
 * </p>
 */
public class MainJavaFx extends Application {

    /** Largeur et hauteur de la fenêtre du jeu */
    public static final int WIDTH = 900, HEIGHT = 580;

    /** La fenêtre principale de l'application */
    private Stage stage;

    /** Niveau courant affiché et joué */
    private int niveauRendu = 1;

    /** Nombre de journaux conservés entre les niveaux */
    private int journauxConserves = 0;

    /** Montant d'argent conservé entre les niveaux */
    private int argentConserves = 0;

    /**
     * Méthode de démarrage de JavaFX.
     *
     * <p>
     * Initialise la fenêtre principale, définit la scène d'accueil et configure
     * la taille et le titre de la fenêtre.
     * </p>
     *
     * @param primaryStage La fenêtre principale
     * @throws IOException Si une ressource nécessaire est introuvable
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        primaryStage.setScene(sceneAccueil()); // Affiche la scène d'accueil
        primaryStage.setTitle("Camelot à vélot"); // Titre de la fenêtre
        primaryStage.setResizable(false); // Empêche le redimensionnement
        primaryStage.show(); // Rend la fenêtre visible
    }

    /**
     * Point d'entrée principal de l'application.
     *
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        launch(); // Démarre l'application JavaFX
    }

    /**
     * Crée et retourne la scène d'accueil du jeu.
     *
     * <p>
     * Affiche le niveau courant au centre de l'écran et lance une transition
     * automatique vers la scène de jeu après 3 secondes.
     * </p>
     *
     * @return La scène d'accueil prête à être affichée
     */
    public Scene sceneAccueil() {
        var root = new StackPane();
        var scene = new Scene(root, WIDTH, HEIGHT);

        // Création d'un canvas pour le rendu graphique
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        // Fond noir
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        // Texte vert affichant le niveau
        context.setFill(Color.GREEN);
        context.setFont(new Font("Arial", 40));
        context.fillText("Niveau " + niveauRendu, (WIDTH/2) - 75, HEIGHT/2);

        // Transition automatique vers la scène de jeu après 3 secondes
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            stage.setScene(sceneJeu());
        });
        pause.play();

        return scene;
    }

    /**
     * Crée et retourne la scène principale du jeu.
     *
     * <p>
     * Cette scène gère :
     * <ul>
     *     <li>La mise à jour et le rendu de la partie</li>
     *     <li>La boucle de jeu via  AnimationTimer</li>
     *     <li>La gestion des entrées clavier</li>
     *     <li>La transition vers l'écran de score ou le niveau suivant</li>
     * </ul>
     * </p>
     *
     * @return La scène de jeu prête à être affichée
     */
    public Scene sceneJeu() {
        Input.reset(); // Réinitialisation des entrées clavier

        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);

        // Création du canvas pour le rendu
        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        // Création d'une nouvelle partie avec les données courantes
        Partie partie = new Partie(niveauRendu, journauxConserves, argentConserves);

        // Boucle principale du jeu
        var timer = new AnimationTimer() {
            long dernierTemps = System.nanoTime();

            @Override
            public void handle(long temps) {
                double deltaTemps = (temps - dernierTemps) * 1e-9; // Conversion en secondes

                partie.update(deltaTemps); // Mise à jour de la logique du jeu
                partie.draw(context);      // Dessin de la partie
                dernierTemps = temps;

                // Gestion de la fin de partie
                if (partie.isTermine()) {
                    niveauRendu++;
                    journauxConserves = partie.journauxRestants();
                    argentConserves = partie.argentRestants();
                    Input.reset();
                    this.stop();  // Arrête l’animation

                    // Transition vers la scène suivante
                    if (journauxConserves == 0) {
                        stage.setScene(sceneScore());
                    } else {
                        stage.setScene(sceneAccueil());
                    }
                }
            }
        };
        timer.start();

        // Gestion des touches pressées
        scene.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                Platform.exit(); // Ferme l'application
            } else {
                Input.setKeyPressed(e.getCode(), true);
            }
        });

        // Gestion des touches relâchées
        scene.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });

        return scene;
    }

    /**
     * Crée et retourne la scène de score affichée à la fin de la partie.
     *
     * <p>
     * Affiche un message de rupture de stock, le total d'argent collecté, et
     * relance l'écran d'accueil après une pause de 3 secondes.
     * </p>
     *
     * @return La scène de score prête à être affichée
     */
    public Scene sceneScore() {
        var root = new StackPane();
        var scene = new Scene(root, WIDTH, HEIGHT);

        var canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        var context = canvas.getGraphicsContext2D();

        // Fond noir
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        // Texte rouge pour l'alerte
        context.setFont(new Font("Arial", 40));
        context.setFill(Color.RED);
        context.fillText("Rupture de stock", (WIDTH/2) - 150, (HEIGHT/2) - 100);

        // Texte vert pour l'argent collecté
        context.setFill(Color.GREEN);
        context.fillText("Argent collecté " + argentConserves + " $", (WIDTH/2) - 160, (HEIGHT/2) + 100);

        // Pause avant retour à l'écran d'accueil
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            niveauRendu = 1;
            argentConserves = 0;
            stage.setScene(sceneAccueil());
        });
        pause.play();

        return scene;
    }
}
