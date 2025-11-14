package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Fenetre extends ObjetStatique {

    public Fenetre() {
        super(new Point2D(100,100) /*position*/, new Point2D(100,100) /*taille*/);
        //todo definir la bonne taille des fenetres
    }

    @Override
    public void draw(GraphicsContext context) {
        //todo
    }

    @Override
    public void interact() {
        //todo (peter la fenetre en gros)
    }
}
