package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Porte extends ObjetStatique {
    public static final int LARGEUR = 143;

    public static final int HAUTEUR = 195;

    protected Image imgPorte = new Image("/porte.png");

    protected int chiffreDePorte;

    protected boolean maisonAbonner;

    public Porte(Point2D position, int chiffreDePorte,boolean maisonAbonner) {
        super(position,new Point2D(LARGEUR, HAUTEUR));
        this.chiffreDePorte = chiffreDePorte;
        this.maisonAbonner = maisonAbonner;
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {

        context.drawImage(imgPorte, camera.coordoEcran(position.getX()) , position.getY(), taille.getX(), taille.getY());

        context.setFill(Color.YELLOW);
        context.setFont(new Font( 40));

        // 3. Position centrée
        double xChiffre = (camera.coordoEcran(position.getX()) + taille.getX() / 2) - 30;  // ajustement au centre
        double yChiffre =position.getY() + 45;

        // 4. Dessin du texte
        context.fillText(String.valueOf(chiffreDePorte), xChiffre, yChiffre);
    }




    @Override
    public void interact() {
        //ne interact pas
    }
}