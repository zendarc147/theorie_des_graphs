package theme2.approche2;

import theme2.approche1.GrapheNonOriente;
import theme2.approche1.ItineraireVoyageur;
import java.io.*;

public class Mt22 {

    public static void main(String[] args) {
        try {
            GrapheNonOriente graphe = new GrapheNonOriente();
            graphe.chargerFichier("data/graphe_reel_theme2.txt");

            System.out.println("=== Recolte des dechets aux points de collecte ===");
            System.out.println("Méthode 2: Arbre Couvrant de Poids Minimum (MST)");
            System.out.println("Graphe: Non orienté");
            System.out.println("Nombre de sommets: " + graphe.getSommet().size());

            String sommetDepart = "0";
            System.out.println("Point de départ: Centre de traitement (sommet " + sommetDepart + ")");

            ItineraireVoyageur itineraire = AlgorithmeMST.calculerItineraireMST(graphe, sommetDepart);

            System.out.println("\n=== Résultat avec Shortcutting ===");
            System.out.println(itineraire);
            System.out.println();
            itineraire.afficherDetails(graphe);

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
