package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Maison implements Drawable {
    private ArrayList<ObjetStatique> objetsMaison = new ArrayList<>();
    private boolean maisonAbonner ;
    private Random aleatoire = new Random();

    public Maison(int position, int chiffrePorte) {

        objetsMaison.add(new Porte(new Point2D(position,MainJavaFx.HEIGHT), chiffrePorte, maisonAbonner));

        int estIlAbonner = aleatoire.nextInt(2);

        if (estIlAbonner == 1) {
            this.maisonAbonner = true;
        } else {
            this.maisonAbonner = false;
        }


        int nbFenetre = aleatoire.nextInt(3);

        if (nbFenetre >= 1) {
            objetsMaison.add(new Fenetre(new Point2D(position + Porte.LARGEUR + 300, 50), maisonAbonner));
        }

        if (nbFenetre == 2) {
            objetsMaison.add(new Fenetre(new Point2D(position + Porte.LARGEUR + 600, 50), maisonAbonner));
        }


        int valeur = aleatoire.nextInt(51) + 20; // 20 à 70
        objetsMaison.add( new BoiteAuxLettres(
                new Point2D(position + Porte.LARGEUR + 200, MainJavaFx.HEIGHT * (valeur / 100.0)), maisonAbonner));
    }


    @Override
    public void draw(GraphicsContext context, Camera camera) {


        for (ObjetStatique objetDeMaison : objetsMaison) {
            objetDeMaison.draw(context, camera);
        }

    }


    public ArrayList<ObjetStatique> getObjetsMaison() {
        return objetsMaison;
    }

    public boolean isMaisonAbonner() {
        return maisonAbonner;
    }
}
