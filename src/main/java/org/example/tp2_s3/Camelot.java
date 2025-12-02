package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;


/**
 * Classe représentant le joueur "Camelot" qui se déplace et saute
 * dans le jeu.
 * <p>
 * Camelot hérite de ObjetEnMouvement et gère :
 * <ul>
 *     <li>Les entrées clavier pour se déplacer à gauche/droite et sauter</li>
 *     <li>L'animation du personnage</li>
 *     <li>Les collisions avec le sol</li>
 * </ul>
 */
public class Camelot extends ObjetEnMouvement {

    /** Indique si Camelot touche le sol pour autoriser un saut */
    protected boolean toucheLeSol;

    /** Touches clavier pour le déplacement */
    protected static KeyCode toucheGauche = KeyCode.LEFT;
    protected static KeyCode toucheDroite = KeyCode.RIGHT;
    protected static KeyCode toucheUP1 = KeyCode.UP;
    protected static KeyCode toucheUP2 = KeyCode.SPACE;

    /** Temps total écoulé depuis le début du jeu (pour l'animation) */
    protected double TempsTotal = 0;

    /** ImageView pour gérer l'animation de Camelot */
    protected ImageView CamelotImage;

    /** Images pour l'animation de marche */
    protected Image img1 = new Image(getClass().getResourceAsStream("/camelot1.png"));
    protected Image img2 = new Image(getClass().getResourceAsStream("/camelot2.png"));

    /**
     * Constructeur de Camelot.
     * Position initiale, taille, et vitesse initiale sont définies ici.
     */
    public Camelot() {
        super(new Point2D(400, MainJavaFx.HEIGHT - 144),
                new Point2D(174, 144),
                new Point2D(400, 0));

        toucheLeSol = true;
        CamelotImage = new ImageView(img1);
    }

    /**
     * Met à jour la position et l'état de Camelot.
     *
     * @param deltaTemps Temps écoulé depuis la dernière frame (en secondes)
     */
    @Override
    public void update(double deltaTemps) {
        boolean gauche = Input.isKeyPressed(toucheGauche);
        boolean droite = Input.isKeyPressed(toucheDroite);

        // Animation du personnage
        TempsTotal += deltaTemps;
        double anim = (TempsTotal * 4) % 2;
        CamelotImage.setImage(anim < 1 ? img1 : img2);

        // Gestion de l'accélération selon les touches pressées
        if (gauche) {
            if (velocite.getX() > 200)
                acceleration = new Point2D(-300, acceleration.getY());
            else
                acceleration = new Point2D(0, acceleration.getY());
        }
        else if (droite) {
            if (velocite.getX() < 600)
                acceleration = new Point2D(300, acceleration.getY());
            else
                acceleration = new Point2D(0, acceleration.getY());
        }
        else { // pas de touche appuyée
            if (velocite.getX() > 400)
                acceleration = new Point2D(-300, acceleration.getY());
            else if (velocite.getX() < 400)
                acceleration = new Point2D(300, acceleration.getY());
            else
                acceleration = new Point2D(0, acceleration.getY());
        }

        // Saut
        boolean jump = Input.isKeyPressed(toucheUP2) || Input.isKeyPressed(toucheUP1);
        if (toucheLeSol && jump) {
            velocite = new Point2D(velocite.getX(), -500);
            toucheLeSol = false;
        }

        // Mise à jour de la vélocité et position
        velocite = velocite.add(acceleration.multiply(deltaTemps));
        position = position.add(velocite.multiply(deltaTemps));

        // Gestion du sol
        if (position.getY() + taille.getY() >= MainJavaFx.HEIGHT) {
            toucheLeSol = true;
            velocite = new Point2D(velocite.getX(), 0);
            position = new Point2D(position.getX(), MainJavaFx.HEIGHT - taille.getY());
        }
    }

    /**
     * Dessine Camelot à l'écran en fonction de la caméra.
     *
     * @param context Contexte graphique du Canvas
     * @param camera  Caméra pour convertir les coordonnées monde → écran
     */
    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.drawImage(CamelotImage.getImage(),
                camera.coordoEcran(position.getX()),
                position.getY(),
                taille.getX(),
                taille.getY());
    }

    /**
     * Retourne la vélocité horizontale actuelle de Camelot.
     *
     * @return Vélocité en X
     */
    public double getVelociteX() {
        return velocite.getX();
    }
}
