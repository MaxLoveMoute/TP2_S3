package org.example.tp2_s3;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

    public class Debogage implements Drawable {

        private ArrayList<Maison> maisons;
        private Camelot camelot;
        private ArrayList<Journal> journaux;

        public Debogage(ArrayList<Maison> maisons, Camelot camelot, ArrayList<Journal> journaux) {
            this.maisons = maisons;
            this.camelot = camelot;
            this.journaux = journaux;
        }

        @Override
        public void draw(GraphicsContext context, Camera camera) {

            context.setStroke(Color.YELLOW);
            context.setLineWidth(2);

            // === 1) OBSTACLES DE TOUTES LES MAISONS ===
            for (Maison maison : maisons) {
                for (ObjetStatique objet : maison.getObjetsMaison()) {



                    double xMonde = objet.getGauche();
                    double yMonde = objet.getHaut();
                    double width  = objet.getDroite() - objet.getGauche();
                    double height = objet.getBas() - objet.getHaut();

                    double xEcran = camera.coordoEcran(xMonde);
                    double yEcran = yMonde;  // ta caméra ne scroll pas verticalement

                    context.strokeRect(xEcran, yEcran, width, height);
                }
            }

            // === 2) LIGNE DEPOSITIONS CAMÉLOT ===
            double xCamelotEcran = camera.coordoEcran(camelot.getGauche());

            context.strokeLine(
                    xCamelotEcran,
                    0,
                    xCamelotEcran,
                    MainJavaFx.HEIGHT
            );

            // === 3) JOURNAUX ===
            for (ObjetEnMouvement journal : journaux) {

                if (journal != null) {

                    double xMonde = journal.getGauche();
                    double yMonde = journal.getHaut();
                    double width = journal.getDroite() - journal.getGauche();
                    double height = journal.getBas() - journal.getHaut();

                    double xEcran = camera.coordoEcran(xMonde);
                    double yEcran = yMonde;

                    context.strokeRect(xEcran, yEcran, width, height);
                }
            }
        }
    }




