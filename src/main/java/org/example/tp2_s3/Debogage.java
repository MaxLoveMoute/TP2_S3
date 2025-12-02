package org.example.tp2_s3;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Classe de débogage qui permet de visualiser des informations utiles
 * à l'écran, comme les obstacles des maisons, la position du Camelot,
 * et les journaux en mouvement.
 * <p>
 * Cette classe est utilisée uniquement à des fins de debug et ne
 * modifie pas le comportement du jeu.
 */
public class Debogage implements Drawable {

    /** Liste des maisons du niveau */
    private ArrayList<Maison> maisons;

    /** Référence au Camelot du jeu */
    private Camelot camelot;

    /** Liste des journaux en mouvement */
    private ArrayList<Journal> journaux;

    /**
     * Constructeur de la classe de débogage.
     *
     * @param maisons Liste des maisons du niveau
     * @param camelot Référence au Camelot
     * @param journaux Liste des journaux à afficher
     */
    public Debogage(ArrayList<Maison> maisons, Camelot camelot, ArrayList<Journal> journaux) {
        this.maisons = maisons;
        this.camelot = camelot;
        this.journaux = journaux;
    }

    /**
     * Dessine les éléments de débogage sur l'écran.
     * <p>
     * Affiche en jaune :
     * <ul>
     *     <li>Les rectangles des obstacles de toutes les maisons</li>
     *     <li>La ligne verticale correspondant à la position du Camelot</li>
     *     <li>Les rectangles représentant les journaux en mouvement</li>
     * </ul>
     *
     * @param context Contexte graphique du Canvas
     * @param camera  Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public void draw(GraphicsContext context, Camera camera) {

        context.setStroke(Color.YELLOW);
        context.setLineWidth(2);

        // === 1) OBSTACLES DE TOUTES LES MAISONS ===
        for (Maison maison : maisons) {
            for (ObjetStatique objet : maison.getObjetsMaison()) {

                double xMonde = objet.getGauche();
                double yMonde = objet.getHaut();
                double width  = objet.getDroite() - objet.getGauche();
                double height = objet.getBas() - objet.getHaut();

                double xEcran = camera.coordoEcran(xMonde);
                double yEcran = yMonde; // pas de scrolling vertical

                context.strokeRect(xEcran, yEcran, width, height);
            }
        }

        // === 2) LIGNE DE POSITION DU CAMÉLOT ===
        double xCamelotEcran = camera.coordoEcran(camelot.getGauche());
        context.strokeLine(
                xCamelotEcran,
                0,
                xCamelotEcran,
                MainJavaFx.HEIGHT
        );

        // === 3) JOURNAUX EN MOUVEMENT ===
        for (ObjetEnMouvement journal : journaux) {

            if (journal != null) {

                double xMonde = journal.getGauche();
                double yMonde = journal.getHaut();
                double width = journal.getDroite() - journal.getGauche();
                double height = journal.getBas() - journal.getHaut();

                double xEcran = camera.coordoEcran(xMonde);
                double yEcran = yMonde;

                context.strokeRect(xEcran, yEcran, width, height);
            }
        }
    }
}
