package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BoiteAuxLettres extends ObjetStatique {

    private Image boiteCourante;
    private Image boiteBlanche = new Image("/boite-aux-lettres.png");
    private Image boiteRouge  = new Image("/boite-aux-lettres-rouge.png");
    private Image boiteVerte  = new Image("/boite-aux-lettres-vert.png");

    private boolean maisonAbonner;


    public BoiteAuxLettres(Point2D position, boolean maisonAbonner) {
        super(position, new Point2D(81, 76));
        this.boiteCourante = boiteBlanche; // image par défaut
        this.maisonAbonner = maisonAbonner;
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.drawImage(
                boiteCourante,
                camera.coordoEcran(position.getX()),
                position.getY()
        );
    }

    @Override
    public void interact(Inventaire inventaire) {
        aInteragi = true;
        if (maisonAbonner) {
            boiteCourante = boiteVerte;
            inventaire.additionOuSoustractionDeDollar(1);
        }else{
            boiteCourante = boiteRouge;
            inventaire.additionOuSoustractionDeDollar(-1);
        }
    }
}

