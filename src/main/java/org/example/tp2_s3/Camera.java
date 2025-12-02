package org.example.tp2_s3;

import javafx.geometry.Point2D;



/**
 * Classe représentant la caméra du jeu.
 * <p>
 * La caméra suit le joueur (Camelot) horizontalement et permet
 * de convertir les coordonnées du monde en coordonnées écran.
 */
public class Camera {

    /** Position horizontale de la caméra dans le monde */
    private double positionEnX;

    /** Largeur de l'écran / du canvas */
    private double widthEcran;

    /** Vélocité actuelle de la caméra (en pixels / seconde) */
    private Point2D velociteDeCamera;

    /**
     * Constructeur de la caméra.
     *
     * @param widthEcran Largeur de l'écran (en pixels)
     */
    public Camera(double widthEcran) {
        this.widthEcran = widthEcran;
        this.positionEnX = 0;
        this.velociteDeCamera = new Point2D(0, 0);
    }

    /**
     * Met à jour la position de la caméra pour suivre Camelot.
     * <p>
     * La caméra garde Camelot à environ 20% de la largeur de l'écran
     * à partir du bord gauche.
     *
     * @param camelot Objet Camelot à suivre
     */
    public void suivre(Camelot camelot) {
        velociteDeCamera = new Point2D(camelot.getVelociteX(), 0);
        positionEnX = camelot.getGauche() - widthEcran * 0.2;
    }

    /**
     * Convertit une position X dans le monde en position X sur l'écran.
     *
     * @param positionObjetX Coordonnée X de l'objet dans le monde
     * @return Coordonnée X de l'objet à l'écran
     */
    public double coordoEcran(double positionObjetX) {
        return positionObjetX - positionEnX;
    }

    /**
     * Retourne la vélocité horizontale actuelle de la caméra.
     *
     * @return Vélocité X de la caméra
     */
    public double getVelociteDeCameraX() {
        return velociteDeCamera.getX();
    }
}
