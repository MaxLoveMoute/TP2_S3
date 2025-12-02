package org.example.tp2_s3;



import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe représentant une fenêtre dans une maison.
 * <p>
 * Une fenêtre peut être "interagie" par le joueur. Selon si la maison est abonnée
 * ou non, l'interaction change la couleur de la fenêtre et affecte le score/dollars du joueur.
 */
public class Fenetre extends ObjetStatique {

    /** Image actuellement affichée pour la fenêtre */
    private Image fenetreCourante;

    /** Image de la fenêtre intacte (blanche) */
    private Image fenetreBlanche = new Image("/fenetre.png");

    /** Image de la fenêtre brisée rouge (si maison abonnée) */
    private Image fenetreRouge  = new Image("/fenetre-brisee-rouge.png");

    /** Image de la fenêtre brisée verte (si maison non abonnée) */
    private Image fenetreVerte  = new Image("/fenetre-brisee-vert.png");

    /** Indique si la maison à laquelle appartient cette fenêtre est abonnée */
    private boolean maisonAbonner;

    /**
     * Constructeur d'une fenêtre.
     *
     * @param position      Position de la fenêtre dans le monde (coordonnées x,y)
     * @param maisonAbonner Indique si la maison est abonnée ou non
     */
    public Fenetre(Point2D position, boolean maisonAbonner) {
        super(position, new Point2D(159, 130)); // Taille fixe de la fenêtre
        this.maisonAbonner = maisonAbonner;
        fenetreCourante = fenetreBlanche; // Initialement intacte
    }

    /**
     * Dessine la fenêtre sur l'écran.
     *
     * @param context Contexte graphique du Canvas
     * @param camera  Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.drawImage(fenetreCourante, camera.coordoEcran(position.getX()), position.getY());
    }

    /**
     * Interaction du joueur avec la fenêtre.
     * <p>
     * Change la couleur de la fenêtre selon l'état de la maison et modifie
     * le score/dollars dans l'inventaire.
     *
     * @param inventaire Inventaire du joueur pour appliquer les effets de l'interaction
     */
    @Override
    public void interact(Inventaire inventaire) {
        aInteragi = true;
        if (maisonAbonner) {
            // Maison abonnée → fenêtre rouge et perte de dollars
            fenetreCourante = fenetreRouge;
            inventaire.additionOuSoustractionDeDollar(-2);
        } else {
            // Maison non abonnée → fenêtre verte et gain de dollars
            fenetreCourante = fenetreVerte;
            inventaire.additionOuSoustractionDeDollar(2);
        }
    }
}
