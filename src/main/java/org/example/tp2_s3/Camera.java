package org.example.tp2_s3;

import javafx.geometry.Point2D;

import javafx.geometry.Point2D;

public class Camera {

    private double positionEnX; // Position de la caméra dans le monde
    private double width;       // Largeur de la caméra (taille de l'écran)



    // Hauteur de la caméra (taille de l'écran)

    public Camera(double width) {
        this.width = width;
        this.positionEnX = 0; // La caméra commence centrée sur le début du Camelot
    }

    /**
     * Met à jour la position de la caméra pour suivre le Camelot uniquement en X
     */
    public void suivre(Camelot camelot) {
        // On centre la caméra sur le début de l'image du Camelot
        this.positionEnX = camelot.getGauche();

        // Limites horizontales pour ne pas sortir du monde
        if (positionEnX > MainJavaFx.WIDTH - width) {
            positionEnX = MainJavaFx.WIDTH - width;
        }
    }

    public double coordoEcran(double positionMondeX) {
        return positionMondeX-(positionEnX);
    }




}
