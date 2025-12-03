package theme3.hypothese2;

import java.io.IOException;

/**
 * Programme de test pour le Thème 3 - Hypothèse 2
 */
public class TestTheme3H2 {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║   THÈME 3 - PLANIFICATION DES JOURS DE COLLECTE          ║");
        System.out.println("║   Hypothèse 2 : Voisinage + Capacité des camions         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        testGrapheFichier();
    }

    private static void testGrapheFichier() {
        try {
            String fichier = "data/graphes_tests/secteurs_h2.txt";
            System.out.println("\nLecture du fichier : " + fichier + "\n");
            GrapheSecteurs graphe = LecteurGrapheSecteurs.lireGraphe(fichier);

            graphe.afficher();

            // Paramètres de l'hypothèse 2
            int capaciteCamion = 100;  // C = 100 unités par camion
            int nombreCamions = 2;      // N = 2 camions disponibles

            ColorationGrapheH2.colorationBFS(graphe, capaciteCamion, nombreCamions);
            ColorationGrapheH2.verifierColoration(graphe, capaciteCamion, nombreCamions);
            graphe.afficherPlanning();

        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
