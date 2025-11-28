package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Partie {

    Random aleatoire = new Random();
    private ArrayList<ObjetEnMouvement> journaux = new ArrayList<ObjetEnMouvement>();
    private ArrayList<Maison> maisons = new ArrayList<Maison>();
    protected static KeyCode toucheLancerJournalVersHaut = KeyCode.Z;
    protected static KeyCode toucheLancerJournalVersBas = KeyCode.X;
    protected static KeyCode toucheLancerJournalFort = KeyCode.SHIFT;

    private long dernierTempsJournalCree = 0;
    private Camera camera;
    private Camelot camelot;
    private double masseDesJournaux;
    private ArrierePlan arrierePlan;

    private Inventaire inventaire;







    Partie() { // On crée les objets pour une partie
        camelot = new Camelot();
        initialiserMaisons();
        masseDesJournaux = determinerMasseJournaux();
        camera = new Camera(MainJavaFx.WIDTH);
        arrierePlan = new ArrierePlan();

    }
    public void update(double deltaTemps) {
        camelot.update(deltaTemps);
        for (var objEnMouvement : journaux) {
            objEnMouvement.update(deltaTemps);
        }

        creerJournal(); //crée les nouveaux journaux si necessaire


        // Tester les collisions
        for (var maison : maisons) {
            Iterator<ObjetEnMouvement> it = journaux.iterator();
            while (it.hasNext()) {
                ObjetEnMouvement journal = it.next();

                for (var objStatique : maison.getObjetsMaison()) {
                    if ( testColision(objStatique, journal)) {
                        it.remove(); // supprime journal de la liste objetsEnMouvement
                        if ((!objStatique.aInteragi)){
                            objStatique.interact(inventaire); //va faire l'action que l'objet statique fait selon son type
                        }
                    }
                }

            }

        }

        //update l'inventaire



        camera.suivre(camelot);


        //todo Autres : vérifie si on a gagné/perdu, ... ______________________________________________________________________
        //checker si il y a 0 journaux left ETTTT 0 dans le tableau journaux

    }


    public void draw(GraphicsContext context) { //todo _____________________________
        context.clearRect(0, 0, MainJavaFx.WIDTH, MainJavaFx.HEIGHT);

        arrierePlan.draw(context, camera);

        for (var maison : maisons) {
            maison.draw(context, camera);
        }

        for (var journal : journaux) {
            journal.draw(context, camera);
        }

        camelot.draw(context, camera);
        inventaire.draw(context);

    }

    public void initialiserMaisons() {
        int chiffrePorte = aleatoire.nextInt(851) + 100;
        ArrayList<Integer> numeroDePorteAbonne = new ArrayList<>();

        for (int i = 1300; i < 13 * 1300; i += 1300) {
            Maison maisonTemps = new Maison(i,chiffrePorte);
            maisons.add(maisonTemps);
            if(maisonTemps.isMaisonAbonner()){
                numeroDePorteAbonne.add(chiffrePorte);
            }
            chiffrePorte += 2;
        }


        inventaire = new Inventaire(12,0,numeroDePorteAbonne);


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



        if ( (deltaTempsJournal >= 0.5) && (creerHaut || creerBas) && (inventaire.getJournal() > 0)) {
            inventaire.additionOuSoustractionDeJournal(-1);
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
