package theme3.hypothese2;

import java.io.IOException;
import java.util.Scanner;

/**
 * Point d'entrée pour le Thème 3 - Hypothèse 2
 * Planification avec contraintes de voisinage ET capacité des camions
 */
public class Main {

    public static void main(String[] args) {
        executer(null);
    }

    public static void executer(Scanner scannerExterne) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║   THÈME 3 - PLANIFICATION DES JOURS DE COLLECTE          ║");
        System.out.println("║   Hypothèse 2 : Voisinage + Capacité des camions         ║");
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
        System.out.println("1. Graphe H2 simple (secteurs_h2.txt)");
        System.out.println("2. Graphe H2 moyen (secteurs_h2_moyen.txt)");
        System.out.println("3. Graphe H2 complexe (secteurs_h2_complexe.txt)");
        System.out.print("\nVotre choix (1-3) : ");

        int choix = Integer.parseInt(scanner.nextLine());

        String fichier;
        String typeCas;

        switch (choix) {
            case 1:
                fichier = "data/graphes_tests/secteurs_h2.txt";
                typeCas = "Graphe H2 simple";
                break;
            case 2:
                fichier = "data/graphes_tests/secteurs_h2_moyen.txt";
                typeCas = "Graphe H2 moyen";
                break;
            case 3:
                fichier = "data/graphes_tests/secteurs_h2_complexe.txt";
                typeCas = "Graphe H2 complexe";
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

            // Paramètres de capacité
            System.out.println("\n=== Configuration des paramètres ===");
            System.out.print("Capacité d'un camion (en unités) [défaut: 100] : ");
            String capaciteStr = scanner.nextLine().trim();
            int capaciteCamion = capaciteStr.isEmpty() ? 100 : Integer.parseInt(capaciteStr);

            System.out.print("Nombre de camions disponibles [défaut: 2] : ");
            String camionsStr = scanner.nextLine().trim();
            int nombreCamions = camionsStr.isEmpty() ? 2 : Integer.parseInt(camionsStr);

            System.out.println("\nParamètres retenus :");
            System.out.println("  - Capacité d'un camion : " + capaciteCamion + " unités");
            System.out.println("  - Nombre de camions : " + nombreCamions);
            System.out.println("  - Capacité totale par jour : " + (capaciteCamion * nombreCamions) + " unités");

            // Colorier avec l'algorithme BFS + contrainte de capacité
            ColorationGrapheH2.colorationBFS(graphe, capaciteCamion, nombreCamions);

            // Vérifier la coloration
            ColorationGrapheH2.verifierColoration(graphe, capaciteCamion, nombreCamions);

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
