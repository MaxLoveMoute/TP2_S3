package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * La classe Journal représente un objet en mouvement simulant un journal
 * dans l'environnement. Elle hérite de  ObjetEnMouvement et inclut
 * des propriétés physiques comme la masse et la charge, ainsi que la gestion
 * de la vélocité maximale et du rendu graphique.
 *
 * <p>
 * Cette classe est conçue pour être utilisée dans un moteur de simulation physique
 * ou un jeu, où le journal peut se déplacer, subir des forces, et être affiché
 * à l'écran via un GraphicsContext.
 * </p>
 */
public class Journal extends ObjetEnMouvement {

    /** L'image représentant le journal à l'écran */
    protected Image imgJournal = new Image("/journal.png");

    /** La masse du journal (en kilogrammes ou unité physique choisie) */
    protected static double masse;

    /** La charge électrique du journal, constante pour tous les journaux */
    public static final int CHARGE = 900;

    /**
     * Constructeur de la classe Journal.
     *
     * @param position La position initiale du journal dans le monde (coordonnées x,y)
     * @param vitesse  La vitesse initiale du journal (vecteur 2D)
     * @param masse    La masse du journal
     */
    public Journal(Point2D position, Point2D vitesse, double masse) {
        super(position, new Point2D(52, 31), vitesse); // taille arbitraire : 52x31
        this.masse = masse;
    }

    /**
     * Met à jour l'état du journal en fonction du temps écoulé.
     *
     * <p>
     * La méthode applique les lois de la cinématique :
     * <ul>
     *   <li>Mise à jour de la vélocité : v = v + a * deltaTemps</li>
     *   <li>Limitation de la vitesse maximale à 1500 unités pour éviter des valeurs extrêmes</li>
     *   <li>Mise à jour de la position : p = p + v * deltaTemps</li>
     * </ul>
     * </p>
     *
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour (en secondes)
     */
    @Override
    public void update(double deltaTemps) {
        velocite = velocite.add(acceleration.multiply(deltaTemps));

        // Limite la vitesse maximale du journal pour éviter des mouvements irréalistes
        if (velocite.magnitude() > 1500) {
            double max = 1500;
            velocite = velocite.multiply(max / velocite.magnitude());
        }

        // Mise à jour de la position selon la vélocité
        position = position.add(velocite.multiply(deltaTemps));
    }

    /**
     * Dessine le journal sur le GraphicsContext fourni en tenant compte
     * de la caméra pour les coordonnées écran.
     *
     * @param context Le contexte graphique sur lequel dessiner le journal
     * @param camera  La caméra permettant de transformer les coordonnées monde en écran
     */
    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.drawImage(imgJournal, camera.coordoEcran(position.getX()), position.getY());
    }

    /**
     * Renvoie la masse actuelle du journal.
     *
     * @return La masse du journal
     */
    public static double getMasse() {
        return masse;
    }
}
