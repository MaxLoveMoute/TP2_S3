package org.example.tp2_s3;


import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class ArrierePlan implements Drawable {

    private static final double WORLD_WIDTH = 14*1300;
    private Point2D tailleBrique = new Point2D(192, 96);
    private Image imageBrique = new Image(("/brique.png"));





    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.setFill(Color.BLACK);
        context.fillRect(0,0,WORLD_WIDTH,MainJavaFx.HEIGHT);

        int nbX = (int)Math.ceil( WORLD_WIDTH / tailleBrique.getX()) + 2;
        int nbY = (int)Math.ceil(MainJavaFx.HEIGHT / tailleBrique.getY())+ 20;

        for (int i = 0; i < nbY; i++) {
            for (int j = 0; j < nbX; j++) {

                double xEcran = camera.coordoEcran(j * tailleBrique.getX());
                double yEcran = i * tailleBrique.getY();

                context.drawImage(imageBrique, xEcran, yEcran);
            }
        }

    }



}
