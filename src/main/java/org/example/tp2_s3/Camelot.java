package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Camelot {
    protected Point2D position = Point2D.ZERO;
    protected Point2D taille = new Point2D(120, 60);

    // Physique
    protected Point2D velocite = new Point2D(400, 0);
    protected Point2D acceleration = Point2D.ZERO;
    protected boolean toucheLeSol;

    protected static KeyCode touchesGauche = KeyCode.LEFT;

    protected static KeyCode touchesDroite  = KeyCode.RIGHT;

    protected static KeyCode touchesUP = KeyCode.UP;

    protected double tempsTotal = 0;

    public Camelot() {
        toucheLeSol = true;
    }

    public void update(double deltaTemps) {
        boolean gauche = Input.isKeyPressed(touchesGauche);
        boolean droite = Input.isKeyPressed(touchesDroite);



            // --- Contrôle clavier pour les autres personnages ---
            if (gauche) {
                velocite = new Point2D(-300, velocite.getY());
            }
            else if (droite) {
                velocite = new Point2D(+300, velocite.getY());
            }
            else {
                velocite = new Point2D(0, velocite.getY());
            }

            boolean jump = Input.isKeyPressed(KeyCode.SPACE)
                    || Input.isKeyPressed(touchesUP);

            if (toucheLeSol && jump) {
                velocite = new Point2D(velocite.getX(), -300);
                toucheLeSol = false;
            }

            velocite = velocite.add(acceleration.multiply(deltaTemps));


        // --- Mise à jour de la position (commune à tous les personnages) ---
        position = position.add(velocite.multiply(deltaTemps));

        // --- Gestion du sol ---
        if (position.getY() + taille.getY() >= MainJavaFx.HEIGHT) {
            toucheLeSol = true;
            velocite = new Point2D(velocite.getX(), 0);
        }

        // --- Empêche de sortir de l’écran ---
        position = new Point2D(
                Math.clamp(position.getX(), 0, MainJavaFx.WIDTH - taille.getX()),
                Math.clamp(position.getY(), 0, MainJavaFx.HEIGHT - taille.getY())
        );
    }
}
