package org.example.tp2_s3;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class ArrierePlan {
    private Point2D tailleBrique = new Point2D(192, 96);
    private Image imageBrique = new Image(getClass().getResourceAsStream("/brique.png"));

    private double positionMurEnX = 0;
    private Point2D velociteMur = new Point2D(0,0); // vitesse visuelle de base du décor

    public void update(double vitesseCamera, double deltaTemps) {
        // vitesse relative décor / caméra
        double vitesseRelative = velociteMur.getX() - vitesseCamera;
        positionMurEnX += -vitesseRelative * deltaTemps;

        positionMurEnX %= tailleBrique.getX();
        if (positionMurEnX < 0){
            positionMurEnX += tailleBrique.getX();
        }
    }


    public void draw(GraphicsContext context) {

        int nbX = (int)Math.ceil(MainJavaFx.WIDTH / tailleBrique.getX()) + 2;
        int nbY = (int)Math.ceil(MainJavaFx.HEIGHT / tailleBrique.getY());

        for (int i = 0; i < nbY; i++) {
            for (int j = 0; j < nbX; j++) {
                double x = j * tailleBrique.getX() - positionMurEnX;
                double y = i * tailleBrique.getY();
                context.drawImage(imageBrique, x, y);
            }
        }
    }
}
