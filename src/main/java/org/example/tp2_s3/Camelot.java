package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Camelot extends ObjetEnMouvement {
    //todo ajouter l'inventaire

    protected boolean toucheLeSol;

    protected static KeyCode toucheGauche = KeyCode.LEFT;

    protected static KeyCode toucheDroite = KeyCode.RIGHT;

    protected static KeyCode toucheUP = KeyCode.UP;

    protected static KeyCode toucheVersHaut = KeyCode.Z;

    protected static KeyCode toucheVersSol = KeyCode.X;
    protected double TempsTotal = 0;

    protected ImageView CamelotImage;

    protected Image img1 = new Image(getClass().getResourceAsStream("/camelot1.png"));

    protected Image img2 = new Image(getClass().getResourceAsStream("/camelot2.png"));


    public Camelot() {
        super(new Point2D(MainJavaFx.HEIGHT, MainJavaFx.WIDTH/2), new Point2D(174, 144), new Point2D(400, 0), new Point2D(0, 0));
        toucheLeSol = true;
        //todo mettre les bonnes valeurs d'initialisation
        CamelotImage = new ImageView(img1);
    }


    @Override
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
                if(velocite.getX() > 200) {
                    acceleration = new Point2D(-300, velocite.getY());
                }else {
                    acceleration = new Point2D(0, velocite.getY());
                }
            }
            else if (droite) {
                if(velocite.getX() < 600) {
                    acceleration = new Point2D(+300, velocite.getY());
                }else {
                    acceleration = new Point2D(0, velocite.getY());
                }
            }else{
                    if(velocite.getX() > 400) {
                        velocite = new Point2D(-300, velocite.getY());
                    }else if(velocite.getX() < 400) {
                        velocite = new Point2D(300, velocite.getY());
                    }
            }


            boolean jump = Input.isKeyPressed(KeyCode.SPACE)
                    || Input.isKeyPressed(toucheUP);

            if (toucheLeSol && jump) {
                acceleration = new Point2D(velocite.getX(), -1500);
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
        // Clear the canvas
        // Clear and fill background
        context.clearRect(0, 0, MainJavaFx.WIDTH, MainJavaFx.HEIGHT);
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, MainJavaFx.WIDTH, MainJavaFx.HEIGHT);

        // Dessiner Camelot en position relative à la caméra
        context.drawImage(
                CamelotImage.getImage(),
              position.getX(),
                position.getY(),
                taille.getX(),
                taille.getY()
        );
    }



}
