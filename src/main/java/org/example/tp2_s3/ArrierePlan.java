package org.example.tp2_s3;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class ArrierePlan implements Drawable {
    private Point2D tailleBrique = new Point2D(192, 96);
    private Image imageBrique = new Image(("/brique.png"));

    private double positionMurEnX = 0;


    @Override
    public void draw(GraphicsContext context, Camera camera) {

        int nbX = (int)Math.ceil(MainJavaFx.WIDTH / tailleBrique.getX()) + 2;
        int nbY = (int)Math.ceil(MainJavaFx.HEIGHT / tailleBrique.getY());

        for (int i = 0; i < nbY; i++) {
            for (int j = 0; j < nbX; j++) {
                context.drawImage(imageBrique, camera.coordoEcran(j*tailleBrique.getX()),i*tailleBrique.getY() );
            }
        }
    }


}
