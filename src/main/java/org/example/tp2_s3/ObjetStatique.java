package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetStatique extends ObjetDuJeu{

    private boolean AInterragi = false;

    public ObjetStatique (Point2D position, Point2D taille) {
        super(position, taille);
    }


    /**
     * Dessine l'objet sur l'écran.
     *
     * À redéfinir dans les sous-classes.
     */

    @Override
    public abstract void draw(GraphicsContext context,Camera camera);


    /**
     * Définit les interactions de l'objet avec les journaux.
     *
     * À redéfinir dans les sous-classes.
     */
    public abstract void interact (boolean maisonAbonneeAuJournal);



}
