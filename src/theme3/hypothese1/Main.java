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

        System.out.println("\n=== Sélection du graphe de secteurs ===");
        System.out.println("1. Graphe simple (secteurs_simple.txt)");
        System.out.println("2. Graphe moyen (secteurs_moyen.txt)");
        System.out.println("3. Graphe complexe (secteurs_complexe.txt)");
        System.out.print("\nVotre choix (1-3) : ");

        int choix = Integer.parseInt(scanner.nextLine());

        String fichier;
        String typeCas;

        switch (choix) {
            case 1:
                fichier = "data/graphes_tests/secteurs_simple.txt";
                typeCas = "Graphe simple";
                break;
            case 2:
                fichier = "data/graphes_tests/secteurs_moyen.txt";
                typeCas = "Graphe moyen";
                break;
            case 3:
                fichier = "data/graphes_tests/secteurs_complexe.txt";
                typeCas = "Graphe complexe";
                break;
            default:
                System.err.println("Choix invalide !");
                return;
        }

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
