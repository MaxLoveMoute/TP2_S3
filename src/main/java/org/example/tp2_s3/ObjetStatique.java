package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Classe abstraite représentant un objet statique dans le jeu.
 * <p>
 * Un objet statique est un objet fixe dans le monde (non mobile) qui peut
 * être dessiné à l'écran et avec lequel le joueur peut interagir.
 * Cette classe sert de base pour des objets comme {@link Porte}.
 */
public abstract class ObjetStatique extends ObjetDuJeu {

    /** Indique si l'objet a déjà été utilisé/interagi */
    protected boolean aInteragi = false;

    /**
     * Constructeur d'un objet statique.
     *
     * @param position Position de l'objet dans le monde (coordonnées x,y)
     * @param taille   Taille de l'objet (largeur, hauteur)
     */
    public ObjetStatique(Point2D position, Point2D taille) {
        super(position, taille); // Appel au constructeur de ObjetDuJeu
    }

    /**
     * Dessine l'objet à l'écran.
     * <p>
     * Cette méthode est abstraite : chaque sous-classe doit la redéfinir
     * pour définir son propre rendu graphique.
     *
     * @param context Contexte graphique du Canvas
     * @param camera  Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public abstract void draw(GraphicsContext context, Camera camera);

    /**
     * Définit les interactions possibles avec l'objet.
     * <p>
     * Cette méthode est abstraite : chaque sous-classe définit comment
     * l'objet interagit avec l'inventaire ou d'autres éléments du jeu.
     *
     * @param inventaire Inventaire du joueur pour l'interaction
     */
    public abstract void interact(Inventaire inventaire);
}
