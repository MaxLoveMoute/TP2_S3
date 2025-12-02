package org.example.tp2_s3;



import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe représentant une boîte aux lettres d'une maison.
 * <p>
 * La boîte aux lettres peut être interactable :
 * lorsqu'un journal est déposé, l'état de la boîte change et
 * l'inventaire du joueur est modifié.
 */
public class BoiteAuxLettres extends ObjetStatique {

    /** Image actuellement affichée */
    private Image boiteCourante;

    /** Images possibles selon l'état */
    private Image boiteBlanche = new Image("/boite-aux-lettres.png");
    private Image boiteRouge  = new Image("/boite-aux-lettres-rouge.png");
    private Image boiteVerte  = new Image("/boite-aux-lettres-vert.png");

    /** Indique si la maison est abonnée (impacte l'interaction) */
    private boolean maisonAbonner;

    /**
     * Constructeur de la boîte aux lettres.
     *
     * @param position Position dans le monde
     * @param maisonAbonner Vrai si la maison est abonnée
     */
    public BoiteAuxLettres(Point2D position, boolean maisonAbonner) {
        super(position, new Point2D(81, 76)); // taille fixe
        this.boiteCourante = boiteBlanche; // image par défaut
        this.maisonAbonner = maisonAbonner;
    }

    /**
     * Dessine la boîte aux lettres à l'écran selon la caméra.
     *
     * @param context Contexte graphique du Canvas
     * @param camera Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.drawImage(
                boiteCourante,
                camera.coordoEcran(position.getX()),
                position.getY()
        );
    }

    /**
     * Interaction avec la boîte aux lettres.
     * <p>
     * Change l'image affichée selon que la maison est abonnée ou non,
     * et modifie l'inventaire du joueur (+1 si abonné, -1 sinon).
     *
     * @param inventaire Inventaire du joueur
     */
    @Override
    public void interact(Inventaire inventaire) {
        aInteragi = true;
        if (maisonAbonner) {
            boiteCourante = boiteVerte;
            inventaire.additionOuSoustractionDeDollar(1);
        } else {
            boiteCourante = boiteRouge;
            inventaire.additionOuSoustractionDeDollar(-1);
        }
    }
}
