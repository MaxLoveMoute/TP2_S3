package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Partie {

    private ArrayList<Journal> journaux = new ArrayList<Journal>();
    private ArrayList<Maison> maisons = new ArrayList<Maison>();
    private ArrayList<ParticuleChargee> particulesChargees = new ArrayList<ParticuleChargee>();

    private static int numDuNiveau;

    protected static KeyCode toucheLancerJournalVersHaut = KeyCode.Z;
    protected static KeyCode toucheLancerJournalVersBas = KeyCode.X;
    protected static KeyCode toucheLancerJournalFort = KeyCode.SHIFT;
    protected static KeyCode toucheActiverDebogage = KeyCode.D;
    protected static KeyCode toucheAjouter10Journaux = KeyCode.Q;
    protected boolean lastToucheAjouter10Journaux = false;
    protected static KeyCode mettreJournauxAZero = KeyCode.K;
    protected boolean lastMettreJournauxAZero = false;
    protected static KeyCode passerProchainNiveau = KeyCode.L;
    protected boolean lastPasserProchainNiveau = false;


    protected final int HAUTEUR_NIVEAU = MainJavaFx.HEIGHT;
    protected final int LARGEUR_NIVEAU = 1300 * 13;


    private long dernierTempsJournalCree = 0;
    private Camera camera;
    private Camelot camelot;
    private double masseDesJournaux;
    private ArrierePlan arrierePlan;

    private Debogage debogage;

    private Inventaire inventaire;
    public boolean termine = false;



    Partie(int numeroDuNiveau, int journauxNiveauAvant) { // On crée les objets pour une partie
        numDuNiveau = numeroDuNiveau;
        initialiserParticules();
        camelot = new Camelot();
        initialiserMaisonsEtInventaire(journauxNiveauAvant);
        masseDesJournaux = determinerMasseJournaux();
        camera = new Camera(MainJavaFx.WIDTH);
        arrierePlan = new ArrierePlan();

    }

    public void update(double deltaTemps) {
        camelot.update(deltaTemps);

        //faire l'update des journaux et des particules chargées

        for (var journal : journaux) {
            Point2D champTotal = new Point2D(0,0);
            for (ParticuleChargee particule : particulesChargees) {
                Point2D champ =  particule.champElectriqueSurJournal(journal.getMilieu());
                champTotal = champTotal.add(champ);
            }
            Point2D force = champTotal.multiply(Journal.CHARGE);
            Point2D accelElectrique = force.multiply(1/journal.getMasse());
            Point2D accelGravitationnel = new Point2D(0, 1500);
            journal.setAcceleration((accelElectrique).add(accelGravitationnel));
            journal.update(deltaTemps);

        }

        creerJournal(); //crée les nouveaux journaux si necessaire


        // Tester les collisions
        for (var maison : maisons) {
            Iterator<Journal> it = journaux.iterator();
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

        //clear journaux hors screen
        for (var journal : journaux) {
            if ((camera.coordoEcran(journal.getDroite()) < 0) || (camera.coordoEcran(journal.getGauche()) > MainJavaFx.WIDTH) ||
                    journal.getHaut() > HAUTEUR_NIVEAU ) {
                journaux.remove(journal);
            }
        }

        camera.suivre(camelot);

        //faire les updates de la classe de debug
        updateDeDebogage();

        //conditions pour terminer la partie
        //regarder si il y a 0 journaux ET 0 dans le tableau journaux ou si le camelot a dépassé la fin du niveau
        if (journauxRestants() == 0) {
            if (journaux.size() == 0) {
                termine = true;
            }
        } else if (camelot.getDroite() > (LARGEUR_NIVEAU + 600)) {
            termine = true;
        }
    }


    public void draw(GraphicsContext context) { //todo _____________________________
        context.clearRect(0, 0, MainJavaFx.WIDTH, MainJavaFx.HEIGHT);
        arrierePlan.draw(context, camera);

        for (var maison : maisons) {
            maison.draw(context, camera);
        }

        for (var particule : particulesChargees) {
            particule.draw(context, camera);
        }

        for (var journal : journaux) {
            journal.draw(context, camera);
        }

        camelot.draw(context, camera);
        inventaire.draw(context);
        drawDebogage(context);


    }

    private void updateDeDebogage() {
        boolean pressed;
        // --- Ajouter 10 journaux ---
        pressed = Input.isKeyPressed(toucheAjouter10Journaux);
        if (pressed && !lastToucheAjouter10Journaux) {
            inventaire.journaux += 10;
        }
        lastToucheAjouter10Journaux = pressed;

        // --- Mettre les journaux à zéro ---
        pressed = Input.isKeyPressed(mettreJournauxAZero);
        if (pressed && !lastMettreJournauxAZero) {
            inventaire.journaux = 0;
        }
        lastMettreJournauxAZero = pressed;

        // --- Passer au prochain niveau ---
        pressed = Input.isKeyPressed(passerProchainNiveau);
        if (pressed && !lastPasserProchainNiveau) {
            termine = true;
        }
        lastPasserProchainNiveau = pressed;
    }



    private void drawDebogage(GraphicsContext context) {
        boolean activerDebogage = Input.isKeyPressed(toucheActiverDebogage);
        if (activerDebogage) {
            debogage = new Debogage(maisons,camelot,journaux);
            debogage.draw(context, camera);
        }
    }


    private void initialiserMaisonsEtInventaire(int journauxNiveauAvant) {
        Random aleatoire = new Random();
        int chiffrePorte = aleatoire.nextInt(851) + 100;
        ArrayList<Integer> numeroDePorteAbonne = new ArrayList<>();

        for (int i = 1300; i < LARGEUR_NIVEAU; i += 1300) {
            Maison maisonTemps = new Maison(i,chiffrePorte);
            maisons.add(maisonTemps);
            if(maisonTemps.isMaisonAbonner()){
                numeroDePorteAbonne.add(chiffrePorte);
            }
            chiffrePorte += 2;
        }
        inventaire = new Inventaire(12 + journauxNiveauAvant,0,numeroDePorteAbonne);
    }


    private boolean testColision(ObjetStatique objStatique, ObjetEnMouvement journal) {
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


    private double determinerMasseJournaux () {
        double masse = 1 + Math.random();
        masse = Math.round(masse * 10000) / 10000.0; //permet de garder 4 décimales maximum
        return masse;
    }


    private void creerJournal () {
        boolean creerHaut = Input.isKeyPressed(toucheLancerJournalVersHaut);
        boolean creerBas = Input.isKeyPressed(toucheLancerJournalVersBas);
        boolean creerFort = Input.isKeyPressed(toucheLancerJournalFort);

        long maintenant = System.nanoTime();
        double deltaTempsJournal = (maintenant - dernierTempsJournalCree) * 1e-9;

        if ( (deltaTempsJournal >= 0.5) && (creerHaut || creerBas) && (inventaire.getJournaux() > 0)) {
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


    private void initialiserParticules () {
        int nbParticules = Math.min(((numDuNiveau-1)*30) , 400);
        Random rnd = new Random();

        for (int i = 0; i < nbParticules; i++) {
            int posX = rnd.nextInt(LARGEUR_NIVEAU);
            int posY = rnd.nextInt(HAUTEUR_NIVEAU);
            Point2D position = new Point2D(posX,posY);
            ParticuleChargee p = new ParticuleChargee(position);
            particulesChargees.add(p);
        }

    }


    public boolean isTermine() {
        return termine;
    }

    public int journauxRestants() {
        return inventaire.getJournaux();
    }
}
