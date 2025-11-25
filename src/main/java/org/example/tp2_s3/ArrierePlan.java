package org.example.tp2_s3;

import com.sun.tools.javac.Main;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ArrierePlan implements Drawable {
    private double tailleImageX = 192, tailleImageY = 96;
    private Image imageBrique = new Image(getClass().getResourceAsStream("/images/brique.png"));

    public ArrierePlan() {

    }
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, MainJavaFx.WIDTH, MainJavaFx.HEIGHT);

        double nombreImageBriqueEnX = MainJavaFx.WIDTH / tailleImageX;
        double nombreImageBriqueEnY = MainJavaFx.HEIGHT / tailleImageY;

        int nbX = (int) Math.ceil(nombreImageBriqueEnX);
        int nbY = (int) Math.ceil(nombreImageBriqueEnY);

        for (int j = 0; j < nbX; j++) {
            for (int i = 0; i < nbY; i++) {
                context.drawImage(imageBrique, j * tailleImageX, i * tailleImageY);
            }
        }
    }

}
