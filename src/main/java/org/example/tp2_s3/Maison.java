package org.example.tp2_s3;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public class Maison {
    Porte porte;

    ArrayList<Fenetre> fenetre;

    BoiteAuxLettres boiteAuLettre;

    Random aleatoire = new Random();

    public Maison(Porte porte) {
        this.porte = porte;
        int nbFenetre = aleatoire.nextInt(2);
        for (int i = 0; i < nbFenetre; i++) {
            fenetre.add(new Fenetre());
        }
        this.boiteAuLettre = new BoiteAuxLettres();
    }

    public void drawMaison(GraphicsContext context, Camera camera) {
        porte.draw(context, camera);
        boiteAuLettre.draw(context, camera);
        for (Fenetre fenetre : fenetre) {
            fenetre.draw(context, camera);
        }
    }
    public void updateCollisionAvecObjetDeMaison(){
        for (Fenetre fenetre : fenetre){
            fenetre.interact();
        }
        boiteAuLettre.interact();
    }
}
