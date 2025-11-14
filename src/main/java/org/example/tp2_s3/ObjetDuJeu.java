package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetDuJeu {
    // position
    protected Point2D position = Point2D.ZERO;
    protected Point2D taille = new Point2D(120, 60);

    // Physique
    protected Point2D velocite = Point2D.ZERO;
    protected Point2D acceleration = new Point2D(0, 600);



    public ObjetDuJeu() {
        position = new Point2D(
                //
                MainJavaFx.WIDTH / 2.0 - taille.getX() / 2.0,
                MainJavaFx.HEIGHT - taille.getY());
    }

    /**
     * Dessine l'objet sur l'écran.
     *
     * À redéfinir dans les sous-classes.
     */
    public abstract void draw(GraphicsContext context);


    /**
     * Met à jour la vitesse selon l'accélération et
     * la position selon la vitesse.
     */
    protected void updatePhysique(double deltaTemps) {
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));
    }


    /**
     * Par défaut, une nouvelle sous-classe de ObjetDuJeu ne va
     * rien faire d'autre que subir les lois de la physique
     *
     */
    public void update(double deltaTemps) {
        updatePhysique(deltaTemps);
    } //todo ajouter le reste dans chaque classe indiv



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
