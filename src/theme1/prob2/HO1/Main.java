package theme1.prob2.HO1;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  PROBLÉMATIQUE 2 : Collecte des poubelles                 ║");
        System.out.println("║  HO1 : Graphe non orienté (rues à double sens)            ║");
        System.out.println("║  CAS IDÉAL : Tous les sommets sont de degrés pairs        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        try {
            // Chargement depuis fichier principal
            System.out.println("\n\n========== Chargement du graphe de test ==========");
            GrapheNonOriente graphe = ChargeurGraphe.chargerDepuisFichier(
                "data/graphe_test_ho1.txt"
            );

            // Affichage de la structure du graphe
            graphe.afficherGraphe();

            // Recherche du cycle eulérien
            List<Integer> parcours = CollectePoubelles_HO1_CasIdeal.trouverCycleEulerien(graphe);

            // Affichage du parcours du camion
            CollectePoubelles_HO1_CasIdeal.afficherParcoursCamion(parcours);

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier : " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("FIN DES TESTS");
        System.out.println("=".repeat(60));
    }
}
