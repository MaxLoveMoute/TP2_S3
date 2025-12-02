package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Classe abstraite représentant un objet en mouvement dans le jeu.
 * <p>
 * Un objet en mouvement possède une position, une taille, une vélocité et
 * une accélération. Il est affecté par les lois de la physique et peut se
 * déplacer dans le monde du jeu.
 */
public abstract class ObjetEnMouvement extends ObjetDuJeu {

    /** Vecteur vitesse de l'objet (pixels/seconde) */
    protected Point2D velocite;

    /** Vecteur accélération de l'objet (pixels/seconde²), par défaut gravité vers le bas */
    protected Point2D acceleration = new Point2D(0, 1500);

    /**
     * Constructeur d'un objet en mouvement.
     *
     * @param position Position initiale dans le monde (coordonnées x,y)
     * @param taille   Taille de l'objet (largeur, hauteur)
     * @param velocite Vecteur vitesse initial de l'objet
     */
    public ObjetEnMouvement(Point2D position, Point2D taille, Point2D velocite) {
        super(position, taille); // Appel au constructeur de ObjetDuJeu
        this.velocite = velocite;
    }

    /**
     * Met à jour l'état de l'objet en fonction du temps écoulé.
     * <p>
     * Cette méthode est abstraite : chaque sous-classe doit définir comment
     * l'objet se déplace et interagit avec le monde en fonction de deltaTemps.
     *
     * @param deltaTemps Temps écoulé depuis la dernière mise à jour (en secondes)
     */
    public abstract void update(double deltaTemps); // TODO : implémenter dans chaque sous-classe

    /**
     * Dessine l'objet à l'écran.
     * <p>
     * Méthode abstraite : chaque sous-classe définit son propre rendu graphique.
     *
     * @param context Contexte graphique du Canvas
     * @param camera  Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public abstract void draw(GraphicsContext context, Camera camera);

    /**
     * Retourne la vélocité actuelle de l'objet.
     *
     * @return Vecteur vitesse
     */
    public Point2D getVelocite() {
        return velocite;
    }

    /**
     * Retourne l'accélération actuelle de l'objet.
     *
     * @return Vecteur accélération
     */
    public Point2D getAcceleration() {
        return acceleration;
    }

    /**
     * Définit une nouvelle vélocité pour l'objet.
     *
     * @param velocite Nouveau vecteur vitesse
     */
    public void setVelocite(Point2D velocite) {
        this.velocite = velocite;
    }

    /**
     * Définit une nouvelle accélération pour l'objet.
     *
     * @param acceleration Nouveau vecteur accélération
     */
    public void setAcceleration(Point2D acceleration) {
        this.acceleration = acceleration;
    }
}
