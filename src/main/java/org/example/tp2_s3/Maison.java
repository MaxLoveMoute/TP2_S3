package org.example.tp2_s3;


import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Classe représentant une maison dans le jeu.
 * <p>
 * Une maison est composée d'une porte, éventuellement de fenêtres et
 * d'une boîte aux lettres. Certaines maisons peuvent être "abonnées"
 * pour refléter un état particulier du jeu.
 */
public class Maison implements Drawable {

    /** Liste des objets statiques qui composent la maison (fenêtres, boîtes aux lettres, etc.) */
    private ArrayList<ObjetStatique> objetsMaison = new ArrayList<>();

    /** La porte de la maison */
    private Porte porte;

    /** Indique si la maison est abonnée ou non */
    private boolean maisonAbonner;

    /** Générateur de nombres aléatoires pour créer des maisons variées */
    private Random aleatoire = new Random();

    /**
     * Constructeur d'une maison.
     * <p>
     * La maison est placée à une position X donnée et reçoit un numéro de porte.
     * Les fenêtres et la boîte aux lettres sont ajoutées aléatoirement.
     *
     * @param position     Position X de la maison dans le monde
     * @param chiffrePorte Numéro de la porte
     */
    public Maison(int position, int chiffrePorte) {
        // Création de la porte
        porte = new Porte(
                new Point2D(position, MainJavaFx.HEIGHT - Porte.HAUTEUR),
                chiffrePorte,
                maisonAbonner
        );

        // Détermination aléatoire si la maison est abonnée
        maisonAbonner = aleatoire.nextInt(2) == 1;

        // Détermination aléatoire du nombre de fenêtres (0, 1 ou 2)
        int nbFenetre = aleatoire.nextInt(3);
        if (nbFenetre >= 1) {
            objetsMaison.add(new Fenetre(
                    new Point2D(position + Porte.LARGEUR + 300, 50),
                    maisonAbonner
            ));
        }
        if (nbFenetre == 2) {
            objetsMaison.add(new Fenetre(
                    new Point2D(position + Porte.LARGEUR + 600, 50),
                    maisonAbonner
            ));
        }

        // Ajout de la boîte aux lettres à une hauteur aléatoire entre 20% et 70% de l'écran
        int valeur = aleatoire.nextInt(51) + 20; // 20 à 70
        objetsMaison.add(new BoiteAuxLettres(
                new Point2D(position + Porte.LARGEUR + 200, MainJavaFx.HEIGHT * (valeur / 100.0)),
                maisonAbonner
        ));
    }

    /**
     * Dessine la maison à l'écran.
     * <p>
     * Dessine tous les objets statiques de la maison ainsi que la porte.
     *
     * @param context Contexte graphique du Canvas
     * @param camera  Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public void draw(GraphicsContext context, Camera camera) {
        for (ObjetStatique objetDeMaison : objetsMaison) {
            objetDeMaison.draw(context, camera);
        }
        porte.draw(context, camera);
    }

    /**
     * Retourne la liste des objets statiques de la maison.
     *
     * @return Liste des objets (fenêtres, boîtes aux lettres, etc.)
     */
    public ArrayList<ObjetStatique> getObjetsMaison() {
        return objetsMaison;
    }

    /**
     * Indique si la maison est abonnée.
     *
     * @return true si la maison est abonnée, false sinon
     */
    public boolean isMaisonAbonner() {
        return maisonAbonner;
    }
}
