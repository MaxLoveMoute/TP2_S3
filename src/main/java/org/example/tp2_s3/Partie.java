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


    protected static KeyCode toucheActiverDebogageFlecheChampElectrique = KeyCode.F;

    protected final int HAUTEUR_NIVEAU = MainJavaFx.HEIGHT;
    protected final int LARGEUR_NIVEAU = 1300 * 13;


    private boolean modeDebogage = false;
    private boolean toucheEnfoncee = false;

    private boolean toucheEnfonceDebogageFleche = false;

    private boolean modeDebogageFleche = false;

    private long dernierTempsJournalCree = 0;
    private Camera camera;
    private Camelot camelot;
    private double masseDesJournaux;
    private ArrierePlan arrierePlan;

    private Debogage debogage;

    private Inventaire inventaire;

    private boolean termine = false;



    Partie(int numeroDuNiveau, int journauxConserves) { // On crée les objets pour une partie
        numDuNiveau = numeroDuNiveau;
        initialiserParticules();
        camelot = new Camelot();
        initialiserMaisons(journauxConserves);
        masseDesJournaux = determinerMasseJournaux();
        camera = new Camera(MainJavaFx.WIDTH);
        arrierePlan = new ArrierePlan();

    }

    public void update(double deltaTemps) {
        camelot.update(deltaTemps);

        //faire l'update des journaux et des particules chargées

        for (var journal : journaux) {
            Point2D champTotal = new Point2D(0, 0);
            for (ParticuleChargee particule : particulesChargees) {
                Point2D champ = particule.champElectriqueSurUnPoint(journal.getMilieu());
                champTotal = champTotal.add(champ);
            }
            Point2D force = champTotal.multiply(Journal.CHARGE);
            Point2D accelElectrique = force.multiply(1 / journal.getMasse());
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
                    if (testColision(objStatique, journal)) {
                        it.remove(); // supprime journal de la liste objetsEnMouvement
                        if ((!objStatique.aInteragi)) {
                            objStatique.interact(inventaire); //va faire l'action que l'objet statique fait selon son type
                        }
                    }
                }

            }

        }

        //clear journaux hors screen
        for (var journal : journaux) {
            if ((camera.coordoEcran(journal.getDroite()) < 0) || (camera.coordoEcran(journal.getGauche()) > MainJavaFx.WIDTH) ||
                    journal.getHaut() > HAUTEUR_NIVEAU) {
                journaux.remove(journal);
            }
        }

        camera.suivre(camelot);


        //checker si il y a 0 journaux left ETTTT 0 dans le tableau journaux ou qu'on a dépassé la fin du niveau
        if (inventaire.getJournaux() == 0) {
            if (journaux.size() == 0) {
                termine = true;
            }
        } else if (camelot.getDroite() > (LARGEUR_NIVEAU + 600)) {
            termine = true;
        }

        updateDeDebogage();
    }


    public void draw(GraphicsContext context) {
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
        drawDebogageFlecheChampElectrique(context);

    }


    public void updateDeDebogage() {
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


    public void drawDebogage(GraphicsContext context) {

        boolean toucheAppuyee = Input.isKeyPressed(toucheActiverDebogage);

        // Déclenche le toggle UNE SEULE FOIS par pression
        if (toucheAppuyee && !toucheEnfoncee) {
            modeDebogage = !modeDebogage;
        }


        // Mémoriser l'état pour éviter répétition
        toucheEnfoncee = toucheAppuyee;

        // Si mode débogage activé
        if (modeDebogage) {
            if (debogage == null) {
                debogage = new Debogage(maisons, camelot, journaux);
            }
            debogage.draw(context, camera);
        }
    }

    public void drawDebogageFlecheChampElectrique(GraphicsContext context) {
        boolean toucheAppuyee = Input.isKeyPressed(toucheActiverDebogageFlecheChampElectrique);

        // Toggle une seule fois
        if (toucheAppuyee && !toucheEnfonceDebogageFleche) {
            modeDebogageFleche = !modeDebogageFleche;
        }

        toucheEnfonceDebogageFleche = toucheAppuyee;

        if (modeDebogageFleche) {

            for (var particule : particulesChargees ) {
                if ((camera.coordoEcran(particule.getDroite()) > 0) && (camera.coordoEcran(particule.getGauche()) < MainJavaFx.WIDTH) &&
                        particule.getHaut() < HAUTEUR_NIVEAU) {

                    for (double x = 0; x < LARGEUR_NIVEAU; x += 50) {
                        for (double y = 0; y < MainJavaFx.HEIGHT; y += 50) {
                            var positionMonde = new Point2D(x, y);
                            Point2D positionEcran = new Point2D(camera.coordoEcran(positionMonde.getX()), positionMonde.getY() );

                            Point2D force = particule.champElectriqueSurUnPoint(positionMonde);

                            UtilitairesDessins.dessinerVecteurForce(
                                    positionEcran,
                                    force,
                                    context
                            );
                        }
                    }
                }
            }
        }
    }


    public void initialiserMaisons(int journauxConserves) {
        Random aleatoire = new Random();
        int chiffrePorte = aleatoire.nextInt(851) + 100;
        ArrayList<Integer> numeroDePorteAbonne = new ArrayList<>();

        for (int i = 1300; i < LARGEUR_NIVEAU; i += 1300) {
            Maison maisonTemps = new Maison(i, chiffrePorte);
            maisons.add(maisonTemps);
            if (maisonTemps.isMaisonAbonner()) {
                numeroDePorteAbonne.add(chiffrePorte);
            }
            chiffrePorte += 2;
        }
        inventaire = new Inventaire((12 + journauxConserves), 0, numeroDePorteAbonne);
    }


    public boolean testColision(ObjetStatique objStatique, ObjetEnMouvement journal) {
        boolean colision = false;
        if ((objStatique.getGauche() <= journal.getDroite()) &&
                (objStatique.getDroite() >= journal.getGauche())) {
            if ((objStatique.getHaut() <= journal.getBas()) &&
                    (objStatique.getBas() >= journal.getHaut())) {
                colision = true;
            }
        }
        return colision;
    }


    public double determinerMasseJournaux() {
        double masse = 1 + Math.random();
        masse = Math.round(masse * 10000) / 10000.0; //permet de garder 4 décimales maximum
        return masse;
    }


    public void creerJournal() {
        boolean creerHaut = Input.isKeyPressed(toucheLancerJournalVersHaut);
        boolean creerBas = Input.isKeyPressed(toucheLancerJournalVersBas);
        boolean creerFort = Input.isKeyPressed(toucheLancerJournalFort);

        long maintenant = System.nanoTime();
        double deltaTempsJournal = (maintenant - dernierTempsJournalCree) * 1e-9;

        if ((deltaTempsJournal >= 0.5) && (creerHaut || creerBas) && (inventaire.getJournaux() > 0)) {
            inventaire.additionOuSoustractionDeJournal(-1);
            Journal j = new Journal(camelot.getMilieu(), camelot.getVelocite(), masseDesJournaux);
            journaux.add(j);
            Point2D momentumAAjouter = new Point2D(900, -900);
            if (creerBas) {
                momentumAAjouter = new Point2D(150, -1100);
            }
            if (creerFort) {
                momentumAAjouter = momentumAAjouter.multiply(1.5);
            }
            j.setVelocite(j.getVelocite().add(momentumAAjouter.multiply(1 / j.getMasse())));
            dernierTempsJournalCree = maintenant;
        }
    }


    public void initialiserParticules() {
        int nbParticules = Math.min(((numDuNiveau - 1) * 30), 400);
        Random rnd = new Random();

        for (int i = 0; i < nbParticules; i++) {
            int posX = rnd.nextInt(LARGEUR_NIVEAU);
            int posY = rnd.nextInt(HAUTEUR_NIVEAU);
            Point2D position = new Point2D(posX, posY);
            ParticuleChargee p = new ParticuleChargee(position);
            particulesChargees.add(p);
        }

    }

    public boolean isTermine() {
        return termine;

    }

    public int journauxRestants(){
        return inventaire.getJournaux();
    }


}
