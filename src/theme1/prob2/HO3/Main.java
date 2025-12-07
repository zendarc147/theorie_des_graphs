package theme1.prob2.HO3;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        executer(null);
    }

    public static void executer(Scanner scannerExterne) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  PROBLÉMATIQUE 2 : Collecte des poubelles                 ║");
        System.out.println("║  HO3 : Graphe mixte (rues variées)                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        Scanner scanner;
        boolean fermerScanner = false;

        if (scannerExterne != null) {
            scanner = scannerExterne;
        } else {
            scanner = new Scanner(System.in);
            fermerScanner = true;
        }

        System.out.println("\n=== Sélection du cas à traiter ===");
        System.out.println("1. CAS 1 - Tous les sommets équilibrés");
        System.out.println("2. CAS 2 - Deux sommets déséquilibrés");
        System.out.println("3. CAS 3 - Cas général");
        System.out.print("\nVotre choix (1-3) : ");

        int choix = Integer.parseInt(scanner.nextLine());

        String fichier;
        String typeCas;

        switch (choix) {
            case 1:
                fichier = "data/HO3/graph_test_HO3_cas1.txt";
                typeCas = "CAS 1 - Tous les sommets équilibrés";
                break;
            case 2:
                fichier = "data/HO3/graph_test_HO3_cas2.txt";
                typeCas = "CAS 2 - Deux sommets déséquilibrés";
                break;
            case 3:
                fichier = "data/HO3/graph_test_HO3_cas3.txt";
                typeCas = "CAS 3 - Cas général";
                break;
            default:
                System.err.println("Choix invalide !");
                return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("TRAITEMENT : " + typeCas);
        System.out.println("=".repeat(60));

        try {
            // Chargement depuis fichier
            GrapheMixte graphe = ChargeurGraphe.chargerDepuisFichier(fichier);

            // Affichage de la structure du graphe
            graphe.afficherGraphe();

            // Vérification des degrés
            CollectePoubelles_HO3_CasIdeal.verifierEquilibreDegres(graphe);

            // Recherche du parcours (tous les cas utilisent le cycle eulérien pour l'instant)
            List<Integer> parcours = CollectePoubelles_HO3_CasIdeal.trouverCycleEulerienMixte(graphe);

            // Affichage du parcours du camion
            CollectePoubelles_HO3_CasIdeal.afficherParcoursCamion(parcours);

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier : " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("FIN DU TRAITEMENT");
        System.out.println("=".repeat(60));

        if (fermerScanner) {
            scanner.close();
        }
    }
}
