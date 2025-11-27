package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Partie {
    private Camelot camelot; //todo vérifier si il faut mettre Camelot dans obj en mouvement
    private ArrayList<ObjetEnMouvement> objetsEnMouvement = new ArrayList<ObjetEnMouvement>();
    private ArrayList<ObjetStatique> objetsStatiques = new ArrayList<ObjetStatique>();


    Partie() { // On crée les objets pour une partie
        camelot = new Camelot();
        initialiserObjStatiques();

    }
    public void update(double deltaTemps) {
        camelot.update(deltaTemps);
        for (var objEnMouvement : objetsEnMouvement) {
            objEnMouvement.update(deltaTemps);
        }


        // Tester les collisions
        ArrayList<ObjetEnMouvement> journauxASupprimer = new ArrayList<ObjetEnMouvement>();

        for (var objStatique : objetsStatiques) {
            Iterator<ObjetEnMouvement> it = objetsEnMouvement.iterator();
            while (it.hasNext()) {
                ObjetEnMouvement objEnMouvement = it.next();

                if (testColision(objStatique, objEnMouvement)) {
                    it.remove(); // supprime objEnMouvement de la liste objetsEnMouvement
                    objStatique.interact(); //va faire l'action que l'objet statique fait selon son type
                }
            }

        }


        //todo Autres : vérifie si on a gagné/perdu, ... ______________________________________________________________________
    }
    public void draw(GraphicsContext context) {
        for (var objStatique : objetsStatiques) {
            objStatique.draw(context);
        }

        for (var objEnMouvement : objetsEnMouvement) {
            objEnMouvement.draw(context);
        }

        camelot.draw(context);

    }

    public void initialiserObjStatiques() {
        //todo va initialiser les portes, fenetres etc
    }

    public boolean testColision(ObjetStatique objStatique, ObjetEnMouvement objEnMouvement) {
        boolean colision = false;
        if ((objStatique.getGauche() <= objEnMouvement.getDroite()) &&
                (objStatique.getDroite() >= objEnMouvement.getGauche()))  {
            if ((objStatique.getHaut() <= objEnMouvement.getBas()) &&
                    (objStatique.getBas() >= objEnMouvement.getHaut())) {
                colision = true;
            }

        }

        return colision;
    }

}
