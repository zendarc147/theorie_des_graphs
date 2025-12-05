package theme1.prob2.HO1;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  PROBLÉMATIQUE 2 : Collecte des poubelles                  ║");
        System.out.println("║  HO1 : Graphe non orienté (rues à double sens)             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Sélection du cas à traiter ===");
        System.out.println("1. CAS 1 - Tous les sommets de degrés pairs (Cycle eulérien)");
        System.out.println("2. CAS 2 - Deux sommets de degrés impairs (Chemin eulérien)");
        System.out.println("3. CAS 3 - Cas général (Postier chinois)");
        System.out.print("\nVotre choix (1-3) : ");

        int choix = scanner.nextInt();
        scanner.close();

        String fichier;
        String typeCas;

        switch (choix) {
            case 1:
                fichier = "data/HO1/graphe_test_H01_cas1.txt";
                typeCas = "CAS 1 - Tous les sommets de degrés pairs";
                break;
            case 2:
                fichier = "data/HO1/graph_test_HO1_cas2.txt";
                typeCas = "CAS 2 - Deux sommets de degrés impairs";
                break;
            case 3:
                fichier = "data/HO1/graph_test_H01_cas3.txt";
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
            GrapheNonOriente graphe = ChargeurGraphe.chargerDepuisFichier(fichier);

            // Affichage de la structure du graphe
            graphe.afficherGraphe();

            // Vérification des degrés
            CollectePoubelles_HO1_CasIdeal.verifierDegresParite(graphe);

            // Recherche du parcours selon le cas
            List<Integer> parcours;
            switch (choix) {
                case 1:
                    parcours = CollectePoubelles_HO1_CasIdeal.trouverCycleEulerien(graphe);
                    break;
                case 2:
                    parcours = CollectePoubelles_HO1_CasIdeal.trouverCheminEulerien(graphe);
                    break;
                case 3:
                    parcours = CollectePoubelles_HO1_CasIdeal.resoudrePostierChinois(graphe);
                    break;
                default:
                    parcours = null;
            }

            // Affichage du parcours du camion
            CollectePoubelles_HO1_CasIdeal.afficherParcoursCamion(parcours);

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier : " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("FIN DU TRAITEMENT");
        System.out.println("=".repeat(60));
    }
}
