package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Classe représentant une particule chargée dans le jeu.
 * <p>
 * Une particule possède une charge électrique, une position dans le monde,
 * un rayon pour son affichage et une couleur aléatoire.
 * Elle peut calculer le champ électrique qu'elle crée en un point donné.
 */
public class ParticuleChargee implements Drawable {

    /** Charge électrique de la particule (constante) */
    public static final int CHARGE = 900;

    /** Constante K utilisée pour le calcul du champ électrique */
    public static final int K = 90;

    /** Couleur utilisée pour dessiner la particule */
    protected Color color;

    /** Position de la particule dans le monde (coordonnées x,y) */
    protected Point2D position;

    /** Rayon de la particule pour l'affichage (cercle) */
    protected int rayon;

    /**
     * Constructeur d'une particule chargée.
     *
     * @param position Position initiale de la particule dans le monde
     */
    public ParticuleChargee(Point2D position) {
        this.position = position;
        this.color = initialiserCouleur(); // couleur aléatoire
        this.rayon = 10;                   // taille affichée de la particule
    }

    /**
     * Calcule le champ électrique créé par cette particule en un point donné.
     * <p>
     * Le champ est calculé selon la loi de Coulomb simplifiée :
     * E = k * Q / r², dirigé radialement depuis la particule.
     *
     * @param positionDuPoint Position du point où calculer le champ
     * @return Vecteur du champ électrique en ce point (Point2D)
     */
    public Point2D champElectriqueSurUnPoint(Point2D positionDuPoint) {
        // Vecteur reliant la particule au point
        Point2D r = positionDuPoint.subtract(position);

        // Distance au carré
        double r2 = r.getX() * r.getX() + r.getY() * r.getY();

        // Sécurité : éviter division par zéro si trop proche
        if (r2 < 1) {
            r2 = 1;
        }

        // Module du champ électrique
        double moduleChamp = (K * CHARGE) / r2;

        // Champ vectoriel : direction r normalisée multipliée par le module
        Point2D champElec = r.normalize().multiply(moduleChamp);

        return champElec;
    }

    /**
     * Dessine la particule à l'écran sous forme de cercle coloré.
     *
     * @param context Contexte graphique du Canvas
     * @param camera  Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.setFill(color);
        context.fillOval(
                camera.coordoEcran(position.getX()) - rayon, // position X centrée
                position.getY() - rayon,                     // position Y centrée
                rayon * 2,                                   // largeur
                rayon * 2                                    // hauteur
        );
    }

    /**
     * Initialise une couleur aléatoire pour la particule.
     * <p>
     * Utilise le système HSB (teinte, saturation, brightness)
     *
     * @return Couleur générée aléatoirement
     */
    private Color initialiserCouleur() {
        double teinte = Math.random() * 360.0; // teinte entre 0 et 360
        return Color.hsb(teinte, 1, 1);         // saturation et luminosité max
    }

    /**
     * Coordonnée X gauche de la particule (pour collision ou affichage)
     *
     * @return Position X gauche
     */
    public double getGauche() {
        return position.getX() - rayon;
    }

    /**
     * Coordonnée Y haute de la particule (pour collision ou affichage)
     *
     * @return Position Y haute
     */
    public double getHaut() {
        return position.getY() + rayon;
    }

    /**
     * Coordonnée X droite de la particule (pour collision ou affichage)
     *
     * @return Position X droite
     */
    public double getDroite() {
        return position.getX() + rayon;
    }
}
