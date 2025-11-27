package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Porte extends ObjetStatique {

    protected Image imgPorte = new Image("/porte.png");

    protected int chiffreDePorte;

    public Porte(Point2D position, Point2D taille, int chiffreDePorte) {
        super(position,taille);
        this.chiffreDePorte = chiffreDePorte;
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {

        double xEcran = camera.coordoEcran(position.getX());
        double yEcran = MainJavaFx.HEIGHT - taille.getY();

        // 1. Dessin de l’image
        context.drawImage(imgPorte, xEcran, yEcran, taille.getX(), taille.getY());

        context.setFill(Color.YELLOW);
        context.setFont(new Font( 40));

        // 3. Position centrée
        double xChiffre = (xEcran + taille.getX() / 2) - 30;  // ajustement au centre
        double yChiffre = yEcran + 45;

        // 4. Dessin du texte
        context.fillText(String.valueOf(chiffreDePorte), xChiffre, yChiffre);
    }




    @Override
    public void interact() {
        //ne interact pas
    }
}