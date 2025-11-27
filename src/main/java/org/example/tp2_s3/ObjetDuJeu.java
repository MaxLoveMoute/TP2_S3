package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import javax.swing.text.html.ImageView;

public abstract class ObjetDuJeu extends Object implements Drawable {
    // position
    protected Point2D position;
    protected Point2D taille;

    //affichage
    protected ImageView image;


    public ObjetDuJeu(Point2D position, Point2D taille) {
        this.position = position;
        this.taille = taille;
    }

    /**
     * Dessine l'objet sur l'écran.
     *
     * À redéfinir dans les sous-classes.
     */

    @Override
    public abstract void draw(GraphicsContext context,Camera camera);




    public double getHaut() {
        return position.getY();
    }
    public double getBas() {
        return position.getY() + taille.getY();
    }
    public double getGauche() {
        return position.getX();
    }
    public double getDroite() {
        return position.getX() + taille.getX();
    }

}
