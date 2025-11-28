package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Maison {


    private ArrayList<Fenetre> fenetre;
    private BoiteAuxLettres boiteAuLettre;
    private ArrayList<ObjetStatique> objetsMaison;


    private Porte porte;

    private boolean maisonAbonner;



    private Random aleatoire = new Random();

    public Maison(int position, int chiffrePorte) {
        this.porte = new Porte(new Point2D(position,0), chiffrePorte, maisonAbonner);
        this.fenetre = new ArrayList<>();

        int estIlAbonner = aleatoire.nextInt(2);

        if (estIlAbonner == 1) {
            this.maisonAbonner = true;
        } else {
            this.maisonAbonner = false;
        }


        int nbFenetre = aleatoire.nextInt(3);

        if (nbFenetre >= 1) {
            fenetre.add(new Fenetre(new Point2D(porte.getDroite() + 300, 50), maisonAbonner));
        }

        if (nbFenetre == 2) {
            fenetre.add(new Fenetre(new Point2D(porte.getDroite() + 600, 50), maisonAbonner));
        }


        int valeur = aleatoire.nextInt(51) + 20; // 20 à 70
        this.boiteAuLettre = new BoiteAuxLettres(
                new Point2D(porte.getDroite() + 200, MainJavaFx.HEIGHT * (valeur / 100.0)), maisonAbonner);
    }

    public void drawMaison(GraphicsContext context, Camera camera) {
        porte.draw(context, camera);
        boiteAuLettre.draw(context, camera);


        for (Fenetre f : fenetre) {
            f.draw(context, camera);
        }

    }

    public void interagirAvecObjetsDeMaison() {
        for (Fenetre f : fenetre) {
            f.interact();
        }
        boiteAuLettre.interact();
    }

    public ArrayList<ObjetStatique> getObjetsMaison() {
        return objetsMaison;
    }
}
