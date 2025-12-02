package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe représentant une partie complète du jeu.
 *
 * <p>
 * Elle gère tous les objets et la logique du niveau :
 * <ul>
 *     <li>Les journaux lancés par le joueur</li>
 *     <li>Les maisons et leurs objets</li>
 *     <li>Les particules chargées (champ électrique)</li>
 *     <li>Le joueur (Camelot) et sa caméra</li>
 *     <li>La gestion des entrées clavier et du débogage</li>
 * </ul>
 * </p>
 */
public class Partie {

    /** Liste des journaux en mouvement dans le niveau */
    private ArrayList<Journal> journaux = new ArrayList<Journal>();

    /** Liste des maisons présentes dans le niveau */
    private ArrayList<Maison> maisons = new ArrayList<Maison>();

    /** Liste des particules chargées (champ électrique) */
    private ArrayList<ParticuleChargee> particulesChargees = new ArrayList<ParticuleChargee>();

    /** Numéro du niveau courant */
    private static int numDuNiveau;

    /** Touches clavier configurables pour les actions du joueur et du débogage */
    private static KeyCode toucheLancerJournalVersHaut = KeyCode.Z;
    private static KeyCode toucheLancerJournalVersBas = KeyCode.X;
    private static KeyCode toucheLancerJournalFort = KeyCode.SHIFT;
    private static KeyCode toucheActiverDebogage = KeyCode.D;
    private static KeyCode toucheAjouter10Journaux = KeyCode.Q;
    private boolean lastToucheAjouter10Journaux = false;
    private static KeyCode toucheMettreJournauxAZero = KeyCode.K;
    private boolean lastMettreJournauxAZero = false;
    private static KeyCode touchePasserProchainNiveau = KeyCode.L;
    private boolean lastPasserProchainNiveau = false;
    private static KeyCode toucheParticulesDeTest = KeyCode.I;
    private boolean particulesInitiales = true;
    private static KeyCode toucheActiverDebogageFlecheChampElectrique = KeyCode.F;

    /** Dimensions du niveau */
    public final int HAUTEUR_NIVEAU = MainJavaFx.HEIGHT;
    public final int LARGEUR_NIVEAU = 1300 * 15;

    /** Modes et états de débogage */
    private boolean modeDebogage = false;
    private boolean toucheEnfoncee = false;
    private boolean toucheEnfonceDebogageFleche = false;
    private boolean modeDebogageFleche = false;

    /** Temps du dernier journal créé (nanoTime) */
    private long dernierTempsJournalCree = 0;

    /** Objets principaux du niveau */
    private Camera camera;
    private Camelot camelot;
    private double masseDesJournaux;
    private ArrierePlan arrierePlan;

    /** Gestion du débogage graphique */
    private Debogage debogage;

    /** Inventaire du joueur (journaux et argent) */
    private Inventaire inventaire;

    /** Indique si la partie est terminée */
    private boolean termine = false;

    /**
     * Constructeur d'une partie.
     *
     * <p>
     * Initialise tous les éléments du niveau selon le numéro de niveau et
     * l'état conservé des journaux et de l'argent.
     * </p>
     *
     * @param numeroDuNiveau Numéro du niveau courant
     * @param journauxConserves Nombre de journaux conservés depuis le niveau précédent
     * @param argentConserve Argent conservé depuis le niveau précédent
     */
    Partie(int numeroDuNiveau, int journauxConserves, int argentConserve) {
        numDuNiveau = numeroDuNiveau;
        initialiserParticules();
        camelot = new Camelot();
        initialiserMaisonsEtInventaire(journauxConserves, argentConserve);
        masseDesJournaux = determinerMasseJournaux();
        camera = new Camera(MainJavaFx.WIDTH);
        arrierePlan = new ArrierePlan();
    }

    /**
     * Met à jour tous les éléments du niveau pour un deltaTemps donné.
     *
     * <p>
     * Gère :
     * <ul>
     *     <li>Mouvement et accélération des journaux (champ électrique + gravité)</li>
     *     <li>Création des nouveaux journaux selon les entrées du joueur</li>
     *     <li>Collisions avec les maisons et interactions</li>
     *     <li>Suppression des journaux hors écran</li>
     *     <li>Suivi de la caméra</li>
     *     <li>Vérification de la fin de partie</li>
     *     <li>Mise à jour des fonctions de débogage</li>
     * </ul>
     * </p>
     *
     * @param deltaTemps Temps écoulé depuis la dernière mise à jour (en secondes)
     */
    public void update(double deltaTemps) {
        camelot.update(deltaTemps);

        // Calcul des forces sur les journaux et mise à jour de leur position
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

        creerJournal(); // Crée de nouveaux journaux si nécessaire

        // Test des collisions avec les objets des maisons
        for (var maison : maisons) {
            Iterator<Journal> it = journaux.iterator();
            while (it.hasNext()) {
                ObjetEnMouvement journal = it.next();
                for (var objStatique : maison.getObjetsMaison()) {
                    if (testColision(objStatique, journal)) {
                        it.remove(); // Supprime le journal de la liste
                        if ((!objStatique.aInteragi)) {
                            objStatique.interact(inventaire); // Interaction avec l'inventaire
                        }
                    }
                }
            }
        }

        // Suppression des journaux hors écran
        for (var journal : journaux) {
            if ((camera.coordoEcran(journal.getDroite()) < 0) ||
                    (camera.coordoEcran(journal.getGauche()) > MainJavaFx.WIDTH) ||
                    journal.getHaut() > HAUTEUR_NIVEAU) {
                journaux.remove(journal);
            }
        }

        camera.suivre(camelot);

        // Vérification de la fin de partie
        if (inventaire.getJournaux() == 0) {
            if (journaux.size() == 0) {
                termine = true;
            }
        } else if (camelot.getDroite() > (LARGEUR_NIVEAU - 650)) {
            termine = true;
        }

        updateDeDebogage(); // Gère les entrées et actions de débogage
    }

    /**
     * Dessine tous les éléments de la partie à l'écran.
     *
     * @param context Contexte graphique de JavaFX
     */
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

    /**
     * Gère les entrées clavier liées au débogage.
     */
    private void updateDeDebogage() {
        boolean pressed;

        // Ajouter 10 journaux
        pressed = Input.isKeyPressed(toucheAjouter10Journaux);
        if (pressed && !lastToucheAjouter10Journaux) {
            inventaire.journaux += 10;
        }
        lastToucheAjouter10Journaux = pressed;

        // Mettre les journaux à zéro
        pressed = Input.isKeyPressed(toucheMettreJournauxAZero);
        if (pressed && !lastMettreJournauxAZero) {
            inventaire.journaux = 0;
        }
        lastMettreJournauxAZero = pressed;

        // Passer au prochain niveau
        pressed = Input.isKeyPressed(touchePasserProchainNiveau);
        if (pressed && !lastPasserProchainNiveau) {
            termine = true;
        }
        lastPasserProchainNiveau = pressed;

        // Instanciation des particules de test
        pressed = Input.isKeyPressed(toucheParticulesDeTest);
        if (pressed && particulesInitiales) {
            particulesInitiales = false;
            particulesDeTest();
        }
    }

    /**
     * Dessine les informations et objets liés au débogage.
     *
     * @param context Contexte graphique
     */
    private void drawDebogage(GraphicsContext context) {
        boolean toucheAppuyee = Input.isKeyPressed(toucheActiverDebogage);

        // Toggle mode débogage une seule fois
        if (toucheAppuyee && !toucheEnfoncee) {
            modeDebogage = !modeDebogage;
        }

        toucheEnfoncee = toucheAppuyee;

        if (modeDebogage) {
            if (debogage == null) {
                debogage = new Debogage(maisons, camelot, journaux);
            }
            debogage.draw(context, camera);
        }
    }

    /**
     * Dessine les flèches représentant le champ électrique des particules.
     *
     * @param context Contexte graphique
     */
    private void drawDebogageFlecheChampElectrique(GraphicsContext context) {
        boolean toucheAppuyee = Input.isKeyPressed(toucheActiverDebogageFlecheChampElectrique);

        // Toggle une seule fois
        if (toucheAppuyee && !toucheEnfonceDebogageFleche) {
            modeDebogageFleche = !modeDebogageFleche;
        }

        toucheEnfonceDebogageFleche = toucheAppuyee;

        if (modeDebogageFleche) {
            for (var particule : particulesChargees) {
                if ((camera.coordoEcran(particule.getDroite()) > 0) && (camera.coordoEcran(particule.getGauche()) < MainJavaFx.WIDTH) &&
                        particule.getHaut() < HAUTEUR_NIVEAU) {

                    for (double x = 0; x < LARGEUR_NIVEAU; x += 50) {
                        for (double y = 0; y < HAUTEUR_NIVEAU; y += 50) {
                            var positionMonde = new Point2D(x, y);
                            Point2D positionEcran = new Point2D(camera.coordoEcran(positionMonde.getX()), positionMonde.getY());

                            Point2D forceTotal = new Point2D(0, 0);
                            for (var particule2 : particulesChargees) {
                                forceTotal = forceTotal.add(particule2.champElectriqueSurUnPoint(positionMonde));
                            }

                            UtilitairesDessins.dessinerVecteurForce(positionEcran, forceTotal, context);
                        }
                    }
                }
            }
        }
    }

    /**
     * Initialise les maisons et l'inventaire du joueur.
     *
     * @param journauxConserves Journaux conservés depuis le niveau précédent
     * @param argentConserve Argent conservé depuis le niveau précédent
     */
    private void initialiserMaisonsEtInventaire(int journauxConserves, int argentConserve) {
        Random aleatoire = new Random();
        int chiffrePorte = aleatoire.nextInt(851) + 100;
        ArrayList<Integer> numeroDePorteAbonne = new ArrayList<>();

        for (int i = 1300; i < LARGEUR_NIVEAU - (2*1300); i += 1300) {
            Maison maisonTemps = new Maison(i, chiffrePorte);
            maisons.add(maisonTemps);
            if (maisonTemps.isMaisonAbonner()) {
                numeroDePorteAbonne.add(chiffrePorte);
            }
            chiffrePorte += 2;
        }
        inventaire = new Inventaire((12 + journauxConserves), argentConserve, numeroDePorteAbonne);
    }

    /**
     * Teste la collision entre un objet statique et un journal.
     *
     * @param objStatique Objet statique (maison ou élément)
     * @param journal Objet en mouvement (journal)
     * @return true si collision, false sinon
     */
    private boolean testColision(ObjetStatique objStatique, ObjetEnMouvement journal) {
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

    /**
     * Détermine une masse aléatoire pour les journaux.
     *
     * @return Masse d'un journal
     */
    private double determinerMasseJournaux() {
        double masse = 1 + Math.random();
        masse = Math.round(masse * 10000) / 10000.0; // 4 décimales max
        return masse;
    }

    /**
     * Crée un nouveau journal si les conditions sont remplies (touche pressée et temps écoulé).
     */
    private void creerJournal() {
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

    /**
     * Initialise les particules chargées aléatoires pour le niveau.
     */
    private void initialiserParticules() {
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

    /**
     * Configure des particules pour le test (rangees en haut et bas).
     */
    private void particulesDeTest() {
        particulesChargees.clear();
        instancierRangeeTest(10);
        instancierRangeeTest(HAUTEUR_NIVEAU - 10);
    }

    /**
     * Crée une rangee de particules à une hauteur donnée.
     *
     * @param hauteur Hauteur de la rangee
     */
    private void instancierRangeeTest(int hauteur) {
        for (int i = 0; i < LARGEUR_NIVEAU; i += 50) {
            particulesChargees.add(new ParticuleChargee(new Point2D(i, hauteur)));
        }
    }

    /** @return true si la partie est terminée */
    public boolean isTermine() {
        return termine;
    }

    /** @return Nombre de journaux restants dans l'inventaire */
    public int journauxRestants() {
        return inventaire.getJournaux();
    }

    /** @return Montant d'argent restant dans l'inventaire */
    public int argentRestants() {
        return inventaire.getArgent();
    }
}
