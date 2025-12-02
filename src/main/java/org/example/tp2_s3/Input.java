package org.example.tp2_s3;

import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe utilitaire pour gérer l'état des touches du clavier.
 * <p>
 * Permet de savoir quelles touches sont actuellement pressées, et de mettre à jour
 * cet état lorsqu'une touche est pressée ou relâchée.
 */
public class Input {

    /** Ensemble des touches actuellement pressées */
    private static Set<KeyCode> touches = new HashSet<>();

    /**
     * Vérifie si une touche spécifique est enfoncée.
     *
     * @param code La touche à vérifier
     * @return true si la touche est pressée, false sinon
     */
    public static boolean isKeyPressed(KeyCode code) {
        return touches.contains(code);
    }

    /**
     * Met à jour l'état d'une touche.
     * <p>
     * Appelée depuis les gestionnaires d'événements clavier du Canvas ou de la scène.
     *
     * @param code   La touche à mettre à jour
     * @param appuie true si la touche est pressée, false si elle est relâchée
     */
    public static void setKeyPressed(KeyCode code, boolean appuie) {
        if (appuie)
            touches.add(code);    // Ajoute la touche au set si pressée
        else
            touches.remove(code); // Retire la touche du set si relâchée
    }

    /**
     * Réinitialise l'état de toutes les touches.
     * <p>
     * Utile au démarrage du jeu ou pour remettre le clavier à zéro.
     */
    public static void reset() {
        touches.clear();
    }
}
