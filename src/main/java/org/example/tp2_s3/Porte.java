package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * Classe représentant une porte dans le jeu.
 * <p>
 * Une porte est un objet statique (hérite de {@link ObjetStatique}) qui possède
 * une image, un numéro et peut appartenir à une maison abonnée.
 */
public class Porte extends ObjetStatique {

    /** Largeur fixe de la porte en pixels */
    public static final int LARGEUR = 143;

    /** Hauteur fixe de la porte en pixels */
    public static final int HAUTEUR = 195;

    /** Image de la porte affichée à l'écran */
    protected Image imgPorte = new Image("/porte.png");

    /** Numéro de la porte affiché au-dessus de l'image */
    protected int chiffreDePorte;

    /** Indique si la maison à laquelle appartient la porte est abonnée */
    protected boolean maisonAbonner;

    /**
     * Constructeur de la porte.
     *
     * @param position       Position de la porte dans le monde (coordonnées x,y)
     * @param chiffreDePorte Numéro affiché sur la porte
     * @param maisonAbonner  Vrai si la maison est abonnée
     */
    public Porte(Point2D position, int chiffreDePorte, boolean maisonAbonner) {
        // Appelle le constructeur de ObjetStatique pour définir position et taille
        super(position, new Point2D(LARGEUR, HAUTEUR));
        this.chiffreDePorte = chiffreDePorte;
        this.maisonAbonner = maisonAbonner;
    }

    /**
     * Dessine la porte à l'écran en utilisant le GraphicsContext fourni.
     * <p>
     * L'image de la porte est affichée, puis le numéro est centré sur la porte.
     *
     * @param context Contexte graphique du Canvas
     * @param camera  Caméra utilisée pour convertir les coordonnées monde → écran
     */
    @Override
    public void draw(GraphicsContext context, Camera camera) {
        // Dessine l'image de la porte à l'écran
        context.drawImage(
                imgPorte,
                camera.coordoEcran(position.getX()), // Conversion X en coordonnées écran
                position.getY(),                     // Position Y (monde → écran)
                taille.getX(),                       // Largeur de l'image
                taille.getY()                        // Hauteur de l'image
        );

        // Paramètres du texte (numéro de la porte)
        context.setFill(Color.YELLOW); // Couleur du texte
        context.setFont(new Font(40)); // Taille de la police

        // Calcul pour centrer le texte sur l'image de la porte
        double xChiffre = (camera.coordoEcran(position.getX()) + taille.getX() / 2) - 30; // Ajustement horizontal
        double yChiffre = position.getY() + 45; // Ajustement vertical

        // Dessin du numéro de la porte
        context.fillText(String.valueOf(chiffreDePorte), xChiffre, yChiffre);
    }

    /**
     * Méthode d'interaction avec un inventaire.
     * <p>
     * Pour une porte, aucune action n'est effectuée lors de l'interaction.
     *
     * @param inventaire Inventaire avec lequel la porte pourrait interagir
     */
    @Override
    public void interact(Inventaire inventaire) {
        // Aucune interaction pour la porte
    }
}