package org.example.tp2_s3;

import com.sun.tools.javac.Main;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ArrierePlan implements Drawable {
    private double tailleImageX = 192, tailleImageY = 96;
    private Image imageBrique = new Image(getClass().getResourceAsStream("/brique.png"));

    private Image imageBriqueTotal;

    public ArrierePlan() {
        imageBriqueTotal = creerArrierePlan();
    }

    public Image creerArrierePlan() {

        // Créer un canvas temporaire où on va dessiner le fond
        Canvas canvasTemp = new Canvas(MainJavaFx.WIDTH, MainJavaFx.HEIGHT);
        GraphicsContext gc = canvasTemp.getGraphicsContext2D();

        // Remplir l’arrière-plan en noir (comme ton draw)
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, MainJavaFx.WIDTH, MainJavaFx.HEIGHT);

        // Calcul du nombre de briques
        int nbX = (int) Math.ceil(MainJavaFx.WIDTH / tailleImageX);
        int nbY = (int) Math.ceil(MainJavaFx.HEIGHT / tailleImageY);

        // Dessin des briques
        for (int j = 0; j < nbX; j++) {
            for (int i = 0; i < nbY; i++) {
                gc.drawImage(imageBrique, j * tailleImageX, i * tailleImageY);
            }
        }

        // Conversion du Canvas en Image
        return canvasTemp.snapshot(null, null);
    }

    @Override
    public void draw(GraphicsContext context) {
        // Maintenant, on dessine juste l'image complète
        context.drawImage(imageBriqueTotal, 0, 0);
    }
}
