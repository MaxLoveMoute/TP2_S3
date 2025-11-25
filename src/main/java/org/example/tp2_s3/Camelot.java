package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Camelot extends ObjetEnMouvement {
    //todo ajouter l'inventaire

    protected boolean toucheLeSol;

    protected static KeyCode toucheGauche = KeyCode.LEFT;

    protected static KeyCode toucheDroite = KeyCode.RIGHT;

    protected static KeyCode toucheUP = KeyCode.UP;

    protected double TempsTotal = 0;

    protected ImageView CamelotImage;

    protected Image img1 = new Image(getClass().getResourceAsStream("/camelot1.png"));

    protected Image img2 = new Image(getClass().getResourceAsStream("/camelot2.png"));


    public Camelot() {
        super(new Point2D(0, 0), new Point2D(120, 60), new Point2D(400, 0), new Point2D(0, 0));
        toucheLeSol = true;
        //todo mettre les bonnes valeurs d'initialisation
    }

    public void update(double deltaTemps) {
        boolean gauche = Input.isKeyPressed(toucheGauche);
        boolean droite = Input.isKeyPressed(toucheDroite);

        TempsTotal += deltaTemps;

        double anim = (TempsTotal * 4) % 2;

        if(anim < 1) {
            CamelotImage.setImage(img1);
        } else {
            CamelotImage.setImage(img2);
        }




            // --- Contrôle clavier pour Camelot ---
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
                    || Input.isKeyPressed(toucheUP);

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


    @Override
    public void draw(GraphicsContext context) {

    }
}
