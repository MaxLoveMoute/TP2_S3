package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Journal extends ObjetEnMouvement {
    protected Image imgJournal = new Image("/journal.png");

    protected static double masse;

    public Journal(Point2D position, Point2D vitesse, double masse) {
        super(position, new Point2D(52, 31), vitesse);
        this.masse = masse;
    }

    @Override
    public void update(double deltaTemps) {
        velocite = velocite.add(acceleration.multiply(deltaTemps));

        if (velocite.magnitude() > 1500) { //met un maximum à la velocité
            double max = 1500;
            velocite = velocite.multiply(max / velocite.magnitude());
        }

        position = position.add(velocite.multiply(deltaTemps));
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.drawImage(imgJournal, camera.coordoEcran( position.getX()), position.getY());
    }

    public static double getMasse() {
        return masse;
    }
}
