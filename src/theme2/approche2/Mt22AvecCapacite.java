package theme2.approche2;

import theme2.approche1.GrapheNonOriente;
import theme2.approche1.ItineraireVoyageur;
import java.io.*;
import java.util.*;

public class Mt22AvecCapacite {

    public static void main(String[] args) {
        try {
            GrapheNonOriente graphe = new GrapheNonOriente();
            graphe.chargerFichier("data/graphe_reel_theme2.txt");//on prends le graphe réel ici

            System.out.println("=== Recolte des dechets aux points de collecte ===");
            System.out.println("Méthode 2: MST avec contrainte de capacité");
            System.out.println("Graphe: Non orienté");
            System.out.println("Nombre de sommets: " + graphe.getSommet().size());

            String depot = "0";
            System.out.println("Dépôt: Sommet " + depot);

            Map<String, Integer> contenances = genererContenancesAleatoires(graphe.getSommet(), depot);//on mets de contenances au hasard
            int capaciteMax = 10;

            System.out.println("\n=== Configuration ===");
            System.out.println("Capacité maximale du camion: " + capaciteMax + " unités");
            System.out.println("\nContenances des points de collecte:");
            for (Map.Entry<String, Integer> entry : contenances.entrySet()) {
                if (!entry.getKey().equals(depot)) {
                    System.out.println("  Point " + entry.getKey() + ": " + entry.getValue() + " unités");
                }
            }

            List<ItineraireVoyageur> tournees = AlgorithmeMST.calculerItineraireMSTAvecCapacite(
                    graphe, depot, contenances, capaciteMax);

            System.out.println("\n=== Résultat: " + tournees.size() + " tournée(s) nécessaire(s) ===\n");

            int distanceTotaleGlobale = 0;
            for (int i = 0; i < tournees.size(); i++) {
                ItineraireVoyageur tournee = tournees.get(i);
                System.out.println("--- Tournée " + (i + 1) + " ---");

                int chargeTournee = calculerChargeTournee(tournee.getChemin(), contenances, depot);
                System.out.println("Charge: " + chargeTournee + "/" + capaciteMax + " unités");

                System.out.println(tournee);
                System.out.println();

                distanceTotaleGlobale += tournee.getTotaleDistance();
            }

            System.out.println("=== Bilan global ===");
            System.out.println("Distance totale de toutes les tournées: " + distanceTotaleGlobale + " m");

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> genererContenancesAleatoires(Set<String> sommets, String depot) {
        Map<String, Integer> contenances = new HashMap<>();
        Random random = new Random(42);

        for (String sommet : sommets) {
            if (sommet.equals(depot)) {
                contenances.put(sommet, 0);
            } else {
                contenances.put(sommet, random.nextInt(5) + 1);
            }
        }

        return contenances;
    }

    private static int calculerChargeTournee(List<String> chemin, Map<String, Integer> contenances, String depot) {
        int charge = 0;
        for (String sommet : chemin) {
            if (!sommet.equals(depot)) {
                charge += contenances.getOrDefault(sommet, 0);
            }
        }
        return charge;
    }
}
