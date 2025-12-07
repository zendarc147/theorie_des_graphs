package theme2.approche1;

import java.io.*;

public class Mt2 {

    public static void main(String[] args) {
        try {
            GrapheNonOriente graphe = new GrapheNonOriente();
            graphe.chargerFichier("data/graphe_reel_theme2.txt");

            System.out.println("=== Recolte des dechets aux points de collecte ===");
            System.out.println("Méthode 1: Plus Proche Voisin ");
            System.out.println("Graphe: Non orienté");
            System.out.println("Nombre de sommets: " + graphe.getSommet().size());
            System.out.println("\n");

            String sommetDepart = "0";
            System.out.println("Point de départ: Centre de traitement (sommet " + sommetDepart + ")");
            System.out.println("\n");

            ItineraireVoyageur itineraire = AlgorithmePlusProcheVoisin.calculerItineraire(graphe, sommetDepart);
            System.out.println(itineraire);

            itineraire.afficherDetails(graphe);

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
