package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetEnMouvement extends ObjetDuJeu {
    // Physique
    protected Point2D velocite;
    protected Point2D acceleration = new Point2D(0, 1500);

    public ObjetEnMouvement(Point2D position, Point2D taille, Point2D velocite) {
        super(position, taille);
        this.velocite = velocite;
    }



    /**
     * Par défaut, une nouvelle sous-classe de ObjetDuJeu ne va
     * rien faire d'autre que subir les lois de la physique
     *
     */
    public abstract void update(double deltaTemps);//todo ajouter le reste dans chaque classe indiv




    /**
     * Dessine l'objet sur l'écran.
     *
     * À redéfinir dans les sous-classes.
     */
    @Override
    public abstract void draw(GraphicsContext context,Camera camera);

    public Point2D getVelocite() {
        return velocite;
    }

    public Point2D getAcceleration() {
        return acceleration;
    }

    public void setVelocite(Point2D velocite) {
        this.velocite = velocite;
    }

    public void setAcceleration(Point2D acceleration) {
        this.acceleration = acceleration;
    }
}

