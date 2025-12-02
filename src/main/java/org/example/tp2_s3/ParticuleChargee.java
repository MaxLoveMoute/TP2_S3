package org.example.tp2_s3;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ParticuleChargee implements Drawable {
    public static final int CHARGE = 900;
    public static final int K = 90;


    protected Color color;
    protected Point2D position;
    protected int rayon;


    public ParticuleChargee (Point2D position) {
        this.position = position;
        this.color = initialiserCouleur();
        this.rayon = 10; // la taille sera un rayon à la place

    }

    public Point2D champElectriqueSurUnPoint(Point2D positionDuPoint) {
        Point2D r = positionDuPoint.subtract(position);
        double r2 = r.getX() * r.getX() + r.getY() * r.getY();

        //tester si distance trop petite et la mettre a 1 le cas échéant
        if (r2 < 1) {
            r2 = 1;
        }

        double moduleChamp = (K*CHARGE)/r2;
        Point2D champElec   = r.normalize().multiply(moduleChamp);

        return champElec;
    }

    @Override
    public void draw(GraphicsContext context, Camera camera) {
        context.setFill(color);
        context.fillOval(
                camera.coordoEcran(position.getX()) - rayon, // x
                position.getY() - rayon, // y
                rayon * 2, // largeur
                rayon * 2 // hauteur
        );
    }


    private Color initialiserCouleur() {
        double teinte = Math.random() * 360.0;// random entre 0 et 360
        return Color.hsb(teinte, 1, 1);
    }


    public double getGauche(){
        return (position.getX() - rayon);
    }

    public double getHaut(){
        return (position.getY() + rayon);
    }

    public double getDroite(){
        return (position.getX() + rayon);
    }

}
