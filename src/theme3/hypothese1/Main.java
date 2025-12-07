package theme3.hypothese1;

import java.io.IOException;
import java.util.Scanner;

/**
 * Point d'entrée pour le Thème 3 - Hypothèse 1
 * Planification des jours de passage avec contrainte de voisinage
 */
public class Main {

    public static void main(String[] args) {
        executer(null);
    }

    public static void executer(Scanner scannerExterne) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║   THÈME 3 - PLANIFICATION DES JOURS DE COLLECTE          ║");
        System.out.println("║   Hypothèse 1 : Contrainte de voisinage uniquement       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        Scanner scanner;
        boolean fermerScanner = false;

        if (scannerExterne != null) {
            scanner = scannerExterne;
        } else {
            scanner = new Scanner(System.in);
            fermerScanner = true;
        }

        String fichier = "data/graphes_tests/secteurs_simple.txt";
        String typeCas = "Contraintes géographiques uniquement";

        System.out.println("\n" + "=".repeat(60));
        System.out.println("TRAITEMENT : " + typeCas);
        System.out.println("=".repeat(60));

        try {
            // Lire le graphe
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
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            System.err.println("Vérifiez que le fichier existe : " + fichier);
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("FIN DU TRAITEMENT");
        System.out.println("=".repeat(60));

        if (fermerScanner) {
            scanner.close();
        }
    }
}
