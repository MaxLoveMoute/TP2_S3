package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Journal extends ObjetEnMouvement {

    protected Image imgJournal = new Image("/journal.png");
    public Journal(Point2D position, Point2D taille, Point2D vitesse, Point2D acceleration) {
        super(position, taille, vitesse, acceleration);

    }

    @Override
    public void draw(GraphicsContext context,Camera camera) {

    }
}
