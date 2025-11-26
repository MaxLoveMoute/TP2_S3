package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetEnMouvement extends ObjetDuJeu {
    // Physique
    protected Point2D velocite;
    protected Point2D acceleration;

    public ObjetEnMouvement(Point2D position, Point2D taille, Point2D velocite, Point2D acceleration) {
        super(position, taille);
        this.velocite = velocite;
        this.acceleration = acceleration;
    }

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




    /**
     * Dessine l'objet sur l'écran.
     *
     * À redéfinir dans les sous-classes.
     */
    public abstract void draw(GraphicsContext context);

}

