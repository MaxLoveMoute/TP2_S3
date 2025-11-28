package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fenetre extends ObjetStatique {

    private Image fenetreCourante;
    private Image fenetreBlanche = new Image("/fenetre.png");
    private Image fenetreRouge  = new Image("/fenetre-brisee-rouge.png");
    private Image fenetreVerte  = new Image("/fenetre-brisee-vert.png");

    private boolean maisonAbonner;
    public Fenetre( Point2D position,boolean maisonAbonner) {
        super(position, new Point2D(159,130));
        fenetreCourante = fenetreBlanche;
        this.maisonAbonner = maisonAbonner;

    }

    @Override
    public void draw(GraphicsContext context,Camera camera) {
        context.drawImage(fenetreCourante, camera.coordoEcran( position.getX()), position.getY());
    }

    @Override
    public void interact() {
        if (maisonAbonner) {
            fenetreCourante = fenetreVerte;
        }else{
            fenetreCourante = fenetreRouge;
        }

    }
}
