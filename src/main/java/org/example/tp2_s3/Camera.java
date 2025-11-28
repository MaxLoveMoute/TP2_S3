package org.example.tp2_s3;

import javafx.geometry.Point2D;

import javafx.geometry.Point2D;

public class Camera {

    private double positionEnX;
    private double widthEcran;

    private Point2D velociteDeCamera;

    public Camera(double widthEcran) {
        this.widthEcran = widthEcran;
        this.positionEnX = 0;
        this.velociteDeCamera = new Point2D(0, 0);
    }

    public void suivre(Camelot camelot) {

        velociteDeCamera = new Point2D(camelot.getVelociteX(), 0);
        positionEnX = camelot.getGauche() - widthEcran * 0.2;
    }

    public double coordoEcran(double positionObjetX){
        return positionObjetX - positionEnX;
    }



    public double getVelociteDeCameraX() {
        return velociteDeCamera.getX();
    }
}
