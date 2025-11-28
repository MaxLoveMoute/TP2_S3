package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Maison {

    private Porte porte;
    private ArrayList<Fenetre> fenetre;
    private BoiteAuxLettres boiteAuLettre;

    private boolean abonner;
    private Random aleatoire = new Random();

    public Maison(Porte porte) {
        this.porte = porte;
        this.fenetre = new ArrayList<>();

        int abonner = aleatoire.nextInt(2);

        if (abonner == 1) {
            this.abonner = true;
        }else{this.abonner = false;}


        int nbFenetre = aleatoire.nextInt(3);

        if (nbFenetre >= 1) {
            fenetre.add(new Fenetre(new Point2D(porte.getDroite() + 300, 50)));
        }

        if (nbFenetre == 2) {
            fenetre.add(new Fenetre(new Point2D(porte.getDroite() + 600, 50)));
        }


        int valeur = aleatoire.nextInt(51) + 20; // 20 à 70
        this.boiteAuLettre = new BoiteAuxLettres(
                new Point2D(porte.getDroite() + 200, MainJavaFx.HEIGHT * (valeur / 100.0)));
    }

    public void drawMaison(GraphicsContext context, Camera camera) {
        porte.draw(context, camera);
        boiteAuLettre.draw(context, camera);

        for (Fenetre f : fenetre) {
            f.draw(context, camera);
        }
    }

    public void updateCollisionAvecObjetDeMaison() {
        for (Fenetre f : fenetre) {
            f.interact(abonner);
        }
        boiteAuLettre.interact(abonner);
    }
}
