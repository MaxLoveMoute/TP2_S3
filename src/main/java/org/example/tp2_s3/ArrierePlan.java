package org.example.tp2_s3;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


/**
 * Classe représentant l'arrière-plan du jeu.
 * <p>
 * Affiche un fond constitué de briques répétées horizontalement et verticalement,
 * sur toute la largeur du monde.
 */
public class ArrierePlan implements Drawable {

    /** Largeur totale du monde (en pixels) */
    private static final double WORLD_WIDTH = 15 * 1300;

    /** Taille d'une brique individuelle */
    private Point2D tailleBrique = new Point2D(192, 96);

    /** Image de la brique */
    private Image imageBrique = new Image("/brique.png");

    /**
     * Dessine l'arrière-plan sur le Canvas.
     * <p>
     * Le fond est noir, puis recouvert de briques répétées.
     *
     * @param context Contexte graphique du Canvas
     * @param camera Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public void draw(GraphicsContext context, Camera camera) {
        // Fond noir
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, WORLD_WIDTH, MainJavaFx.HEIGHT);

        // Nombre de briques à dessiner horizontalement et verticalement
        int nbX = (int) Math.ceil(WORLD_WIDTH / tailleBrique.getX()) + 2;
        int nbY = (int) Math.ceil(MainJavaFx.HEIGHT / tailleBrique.getY());

        // Dessin des briques
        for (int i = 0; i < nbY; i++) {
            for (int j = 0; j < nbX; j++) {
                double xEcran = camera.coordoEcran(j * tailleBrique.getX());
                double yEcran = i * tailleBrique.getY();

                context.drawImage(imageBrique, xEcran, yEcran);
            }
        }
    }
}
