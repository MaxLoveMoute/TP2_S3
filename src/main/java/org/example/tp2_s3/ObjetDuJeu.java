package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import javax.swing.text.html.ImageView;

/**
 * Classe abstraite de base pour tous les objets du jeu.
 * <p>
 * Un objet du jeu possède une position et une taille dans le monde,
 * et peut être dessiné à l'écran. Cette classe sert de parent pour
 * les objets statiques et mobiles.
 */
public abstract class ObjetDuJeu implements Drawable {

    /** Position de l'objet dans le monde (coordonnées x,y) */
    protected Point2D position;

    /** Taille de l'objet (largeur, hauteur) */
    protected Point2D taille;

    /** Image associée à l'objet (optionnelle) pour l'affichage */
    protected ImageView image;

    /**
     * Constructeur d'un objet du jeu.
     *
     * @param position Position initiale dans le monde (coordonnées x,y)
     * @param taille   Taille de l'objet (largeur, hauteur)
     */
    public ObjetDuJeu(Point2D position, Point2D taille) {
        this.position = position;
        this.taille = taille;
    }

    /**
     * Dessine l'objet sur l'écran.
     * <p>
     * Méthode abstraite : chaque sous-classe doit définir son propre rendu.
     *
     * @param context Contexte graphique du Canvas
     * @param camera  Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public abstract void draw(GraphicsContext context, Camera camera);

    /**
     * Retourne la coordonnée Y du haut de l'objet.
     *
     * @return Coordonnée Y haute
     */
    public double getHaut() {
        return position.getY();
    }

    /**
     * Retourne la coordonnée Y du bas de l'objet.
     *
     * @return Coordonnée Y basse
     */
    public double getBas() {
        return position.getY() + taille.getY();
    }

    /**
     * Retourne la coordonnée X gauche de l'objet.
     *
     * @return Coordonnée X gauche
     */
    public double getGauche() {
        return position.getX();
    }

    /**
     * Retourne la coordonnée X droite de l'objet.
     *
     * @return Coordonnée X droite
     */
    public double getDroite() {
        return position.getX() + taille.getX();
    }

    /**
     * Retourne le point central de l'objet.
     *
     * @return Coordonnées du milieu (centre X,Y)
     */
    public Point2D getMilieu() {
        return new Point2D(
                getGauche() + (taille.getX() / 2),
                getHaut() + (taille.getY() / 2)
        );
    }
}
