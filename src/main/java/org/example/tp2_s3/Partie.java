package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Partie {
    private ArrayList<ObjetEnMouvement> journaux = new ArrayList<ObjetEnMouvement>();
    private ArrayList<ObjetStatique> objetsStatiques = new ArrayList<ObjetStatique>();

    protected static KeyCode toucheLancerJournalVersHaut = KeyCode.Z;
    protected static KeyCode toucheLancerJournalVersBas = KeyCode.X;
    protected static KeyCode toucheLancerJournalFort = KeyCode.SHIFT;

    private long dernierTempsJournalCree = 0;
    private Camelot camelot;
    private double masseDesJournaux;





    Partie() { // On crée les objets pour une partie
        camelot = new Camelot();
        initialiserObjStatiques();
        masseDesJournaux = determinerMasseJournaux();
    }
    public void update(double deltaTemps) {
        camelot.update(deltaTemps);
        for (var objEnMouvement : journaux) {
            objEnMouvement.update(deltaTemps);
        }

        creerJournal(); //crée les nouveaux journaux si necessaire

        // Tester les collisions
        ArrayList<ObjetEnMouvement> journauxASupprimer = new ArrayList<ObjetEnMouvement>();

        for (var objStatique : objetsStatiques) {
            Iterator<ObjetEnMouvement> it = journaux.iterator();
            while (it.hasNext()) {
                ObjetEnMouvement journal = it.next();

                if (testColision(objStatique, journal)) {
                    it.remove(); // supprime journal de la liste objetsEnMouvement
                    objStatique.interact(); //va faire l'action que l'objet statique fait selon son type
                }
            }

        }


        //todo Autres : vérifie si on a gagné/perdu, ... ______________________________________________________________________
    }

    public void draw(GraphicsContext context) { //todo _____________________________
        /*
        for (var objStatique : objetsStatiques) {
            objStatique.draw(context);
        }

        for (var journal : journaux) {
            journal.draw(context);
        }

        camelot.draw(context);

         */

    }

    public void initialiserObjStatiques() {
        //todo va initialiser les portes, fenetres, etc
    }

    public boolean testColision(ObjetStatique objStatique, ObjetEnMouvement journal) {
        boolean colision = false;
        if ((objStatique.getGauche() <= journal.getDroite()) &&
                (objStatique.getDroite() >= journal.getGauche()))  {
            if ((objStatique.getHaut() <= journal.getBas()) &&
                    (objStatique.getBas() >= journal.getHaut())) {
                colision = true;
            }

        }

        return colision;
    }

    public double determinerMasseJournaux () {
        double masse = 1 + Math.random();
        masse = Math.round(masse * 10000) / 10000.0; //permet de garder 4 décimales maximum
        return masse;
    }






    public void creerJournal () {
        boolean creerHaut = Input.isKeyPressed(toucheLancerJournalVersHaut);
        boolean creerBas = Input.isKeyPressed(toucheLancerJournalVersBas);
        boolean creerFort = Input.isKeyPressed(toucheLancerJournalFort);


        long maintenant = System.nanoTime();
        double deltaTempsJournal = (maintenant - dernierTempsJournalCree) * 1e-9;



        if ( (deltaTempsJournal >= 0.5) && (creerHaut || creerBas)) {
            Journal j = new Journal(camelot.getMilieu(), camelot.getVelocite(), masseDesJournaux);
            journaux.add(j);

            Point2D momentumAAjouter = new Point2D(900,-900);
            if (creerBas) {
                momentumAAjouter = new Point2D(150,-1100);
            }
            if (creerFort) {
                momentumAAjouter = momentumAAjouter.multiply(1.5);
            }

            j.setVelocite(j.getVelocite().add(momentumAAjouter.multiply(1/j.getMasse())));
            dernierTempsJournalCree = maintenant;

        }


    }





}
