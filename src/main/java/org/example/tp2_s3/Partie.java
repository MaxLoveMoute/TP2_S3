package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Partie {
    private Camelot camelot; //todo vérifier si il faut mettre Camelot dans obj en mouvement
    private ArrayList<ObjetEnMouvement> objetsEnMouvement = new ArrayList<ObjetEnMouvement>();
    private ArrayList<ObjetStatique> objetsStatiques = new ArrayList<ObjetStatique>();





    Partie() { // On crée les objets pour une partie
        camelot = new Camelot();
        for (int i = 0; i < flocons.length; i++) {
            Random rnd = new Random();
            int choix = rnd.nextInt(3);

            switch (choix) {
                case 0 -> flocons[i] = new Flocon();
                case 1 -> flocons[i] = new Grele();
                case 2 -> flocons[i] = new FloconOscillant();
            }
        }

    }
    public void update(double deltaTemps) {
        camelot.update(deltaTemps);
        for (var flocon : flocons) {
            flocon.update(deltaTemps);
        }
        // Tester les collisions
        for (var flocon : flocons) {
            double xFloc = flocon.position.getX();
            double xFlocMax = flocon.position.getX() + flocon.taille.getX();
            double xPerso = camelot.position.getX();
            double xPersoMax = camelot.position.getX() + camelot.taille.getX();

            if (xFlocMax > xPerso && xFloc < xPersoMax) {
                double yFloc = flocon.position.getY();
                double yFlocMax = flocon.position.getY() + flocon.taille.getY();
                double yPerso = camelot.position.getY();
                double yPersoMax = camelot.position.getY() + camelot.taille.getY();

                if (yFlocMax > yPerso && yFloc < yPersoMax) {
                    flocon.collisionAvecSol();
                }
            }

        }
        // Autres : vérifie si on a gagné/perdu, ...
        //todo ________________________________________________________________________________________
    }
    public void draw(GraphicsContext context) {
        for (var personnage : personnages) {
            personnage.update(deltaTemps);
        }

        for (var flocon : flocons) {
            flocon.update(deltaTem);
        }

    }



}
