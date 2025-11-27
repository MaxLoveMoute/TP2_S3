package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Porte extends ObjetStatique {

    protected Image imgPorte = new Image("/images/porte.png");
    protected ImageView imagePorteView = new ImageView(imgPorte);

    public Porte(Point2D positionPorte, Point2D positionTaille) {
        super(positionPorte,positionTaille);
    }

    @Override
    public void draw(GraphicsContext context,Camera camera) {

    }

    @Override
    public void interact() {

    }
}
