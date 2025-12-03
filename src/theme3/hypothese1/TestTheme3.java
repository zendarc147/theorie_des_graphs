package theme3.hypothese1;

import java.io.IOException;

/**
 * Programme de test pour le Thème 3 - Hypothèse 1
 * Planification des jours de passage dans les différents quartiers
 * Contrainte : Deux secteurs voisins ne doivent pas être collectés le même jour
 */
public class TestTheme3 {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║   THÈME 3 - PLANIFICATION DES JOURS DE COLLECTE          ║");
        System.out.println("║   Hypothèse 1 : Contrainte de voisinage uniquement       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        // Test : Graphe depuis un fichier avec BFS
        testGrapheFichier();
    }

    /**
     * Test : Lire un graphe depuis un fichier et le colorier avec BFS
     */
    private static void testGrapheFichier() {
        try {
            // Lire le graphe
            String fichier = "data/graphes_tests/secteurs_simple.txt";
            System.out.println("\nLecture du fichier : " + fichier + "\n");
            GrapheSecteurs graphe = LecteurGrapheSecteurs.lireGraphe(fichier);

            // Afficher le graphe
            graphe.afficher();

            // Colorier avec l'algorithme BFS
            ColorationGraphe.colorationBFS(graphe);

            // Vérifier la coloration
            ColorationGraphe.verifierColoration(graphe);

            // Afficher le planning
            graphe.afficherPlanning();

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            System.out.println("Le fichier doit être dans : data/graphes_tests/secteurs_simple.txt");
        }
    }
}
