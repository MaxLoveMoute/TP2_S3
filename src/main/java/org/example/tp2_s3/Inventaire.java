package org.example.tp2_s3;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Inventaire {

    private int journal;

    private int argent;

    private ArrayList<Integer> numeroPorte;

    private Image imageJournal = new Image("/icone-journal.png");
    private Image imageArgent = new Image("/icone-dollar.png");
    private Image imageMaison = new Image("/icone-maison.png");


    public Inventaire(int journal, int argent, ArrayList<Integer> numeroPorte) {
        this.journal = journal;
        this.argent = argent;
        this.numeroPorte = numeroPorte;
    }


    public void draw(GraphicsContext context) {

        // --- 1. Bande noire transparente ---
        double bandeHauteur = 36;
        context.setFill(Color.rgb(0, 0, 0, 0.4));  // noir léger transparent
        context.fillRect(0, 0, MainJavaFx.WIDTH, bandeHauteur);

        // --- 2. Style du texte ---
        context.setFill(Color.LIGHTGRAY);  // gris pâle
        double fontSize = bandeHauteur * 0.6;  // 60% de la hauteur de la bande
        context.setFont(new Font(fontSize));

        // --- 3. Dessiner le texte et les images ---
        // Exemple : journal
        context.drawImage(imageJournal, 15, 0);
        context.fillText(Integer.toString(journal), (115+15)/2, bandeHauteur*0.7);

        // Argent
        context.drawImage(imageArgent, 115, 0);
        context.fillText(Integer.toString(argent), (115+215)/2 + 10, bandeHauteur*0.7);

        // Maison
        context.drawImage(imageMaison, 215, 0);

        // Portes
        int intitialisationDeEmplacementPorte = 215+50;
        for (Integer numero : numeroPorte) {
            context.fillText(Integer.toString(numero), intitialisationDeEmplacementPorte, bandeHauteur * 0.7);
            intitialisationDeEmplacementPorte += 50;
        }
    }

    public void additionOuSoustractionDeDollar(int nbDollar){
        argent += nbDollar;
    }
    public void additionOuSoustractionDeJournal(int nbJournal){
        journal += nbJournal;
    }


}
