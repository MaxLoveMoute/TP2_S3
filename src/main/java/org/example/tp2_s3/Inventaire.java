package org.example.tp2_s3;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;


/**
 * Classe représentant l'inventaire du joueur.
 * <p>
 * Cet inventaire stocke le nombre de journaux, l’argent, et les numéros des maisons (portes)
 * déjà visitées / acquises. Il fournit aussi une méthode pour dessiner l’interface de l’inventaire.
 */
public class Inventaire {

    /** Nombre de journaux que le joueur possède */
    protected int journaux;

    /** Montant d’argent que le joueur possède */
    protected int argent;

    /** Liste des numéros de porte des maisons visitées / acquises */
    protected ArrayList<Integer> numeroPorte;

    /** Image représentant l’icône journal dans l’interface */
    protected Image imageJournal = new Image("/icone-journal.png");

    /** Image représentant l’icône dollar dans l’interface */
    protected Image imageArgent = new Image("/icone-dollar.png");

    /** Image représentant l’icône maison (porte) dans l’interface */
    protected Image imageMaison = new Image("/icone-maison.png");

    /**
     * Constructeur de l’inventaire.
     *
     * @param journaux    Nombre initial de journaux
     * @param argent      Montant initial d’argent
     * @param numeroPorte Liste initiale des numéros de porte (maisons)
     */
    public Inventaire(int journaux, int argent, ArrayList<Integer> numeroPorte) {
        this.journaux = journaux;
        this.argent = argent;
        this.numeroPorte = numeroPorte;
    }

    /**
     * Dessine l’interface de l’inventaire en haut de l'écran.
     * <p>
     * Affiche une bande semi‑transparente en haut, puis les icônes + valeurs de
     * journaux, argent et maisons possédées.
     *
     * @param context Contexte graphique du Canvas
     */
    public void draw(GraphicsContext context) {
        // --- 1. Bande noire transparente en haut de l'écran ---
        double bandeHauteur = 36;
        context.setFill(Color.rgb(0, 0, 0, 0.4));  // noir avec opacité 40%
        context.fillRect(0, 0, MainJavaFx.WIDTH, bandeHauteur);

        // --- 2. Style du texte (gris pâle, taille relative à la bande) ---
        context.setFill(Color.LIGHTGRAY);
        double fontSize = bandeHauteur * 0.6;
        context.setFont(new Font(fontSize));

        // --- 3. Dessin des icônes et des valeurs associées ---

        // Icône journal + nombre de journaux
        context.drawImage(imageJournal, 15, 0);
        context.fillText(Integer.toString(journaux), (115 + 15) / 2, bandeHauteur * 0.7);

        // Icône argent + montant d'argent
        context.drawImage(imageArgent, 115, 4);
        context.fillText(
                Integer.toString(argent),
                (115 + 215) / 2 + 10,
                bandeHauteur * 0.7
        );

        // Icône maison (porte)
        context.drawImage(imageMaison, 215, 0);

        // Nombres des portes + espacement
        int emplacementPorteX = 215 + 50;
        for (Integer numero : numeroPorte) {
            context.fillText(Integer.toString(numero), emplacementPorteX, bandeHauteur * 0.7);
            emplacementPorteX += 50;
        }
    }

    /**
     * Modifie l’argent du joueur en ajoutant (ou soustrayant si négatif).
     *
     * @param nbDollar Nombre de dollars à ajouter (ou soustraire)
     */
    public void additionOuSoustractionDeDollar(int nbDollar) {
        argent += nbDollar;
    }

    /**
     * Modifie le nombre de journaux du joueur.
     *
     * @param nbJournal Nombre de journaux à ajouter (ou soustraire)
     */
    public void additionOuSoustractionDeJournal(int nbJournal) {
        journaux += nbJournal;
    }

    /**
     * Retourne le nombre de journaux que le joueur possède.
     *
     * @return Nombre de journaux
     */
    public int getJournaux() {
        return journaux;
    }
}
