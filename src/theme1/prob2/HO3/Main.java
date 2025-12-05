package theme1.prob2.HO3;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  PROBLÉMATIQUE 2 : Collecte des poubelles                 ║");
        System.out.println("║  HO3 : Graphe mixte (rues variées)                        ║");
        System.out.println("║  CAS IDÉAL : Tous les sommets équilibrés (deg_in=deg_out) ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        try {
            // Chargement depuis fichier principal
            System.out.println("\n\n========== Chargement du graphe de test ==========");
            GrapheMixte graphe = ChargeurGraphe.chargerDepuisFichier(
                "data/graphe_test_ho3.txt"
            );

            // Affichage de la structure du graphe
            graphe.afficherGraphe();

            // Recherche du cycle eulérien mixte
            List<Integer> parcours = CollectePoubelles_HO3_CasIdeal.trouverCycleEulerienMixte(graphe);

            // Affichage du parcours du camion
            CollectePoubelles_HO3_CasIdeal.afficherParcoursCamion(parcours);

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier : " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("FIN DES TESTS");
        System.out.println("=".repeat(60));
    }
}
