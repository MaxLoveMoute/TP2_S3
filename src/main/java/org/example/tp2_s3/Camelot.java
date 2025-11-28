package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Camelot extends ObjetEnMouvement {
    //todo ajouter l'inventaire
    protected boolean toucheLeSol;
    protected static KeyCode toucheGauche = KeyCode.LEFT;
    protected static KeyCode toucheDroite = KeyCode.RIGHT;
    protected static KeyCode toucheUP1 = KeyCode.UP;
    protected static KeyCode toucheUP2 = KeyCode.SPACE;


    protected double TempsTotal = 0;
    protected ImageView CamelotImage;
    protected Image img1 = new Image(getClass().getResourceAsStream("/camelot1.png"));
    protected Image img2 = new Image(getClass().getResourceAsStream("/camelot2.png"));

    public Camelot() {
        super(new Point2D(400, MainJavaFx.HEIGHT - 144), new Point2D(174, 144),
                new Point2D(400, 0));
        toucheLeSol = true;
        CamelotImage = new ImageView(img1);
    }

    @Override
    public void update(double deltaTemps) {
        boolean gauche = Input.isKeyPressed(toucheGauche);
        boolean droite = Input.isKeyPressed(toucheDroite);

        TempsTotal += deltaTemps;
        double anim = (TempsTotal * 4) % 2;
        CamelotImage.setImage(anim < 1 ? img1 : img2);


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
        else {
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







    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.drawImage(CamelotImage.getImage(),
                camera.coordoEcran(position.getX()),
                position.getY(),
                taille.getX(),
                taille.getY());
    }

    public double getVelociteX() {
        return velocite.getX();
    }




}
