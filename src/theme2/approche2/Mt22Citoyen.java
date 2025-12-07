package theme2.approche2;

import theme2.approche1.GrapheNonOriente;
import theme2.approche1.ItineraireVoyageur;
import java.io.*;
import java.util.*;

public class Mt22Citoyen {

    public static void main(String[] args) {
        try {
            GrapheNonOriente graphe = new GrapheNonOriente();
            graphe.chargerFichier("data/graphe_reel_theme2.txt");

            System.out.println("╔════════════════════════════════════════════════════════════╗");
            System.out.println("║   INFORMATION : Comparaison des Tournées de Collecte      ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("📍 Nombre de points de collecte : " + (graphe.getSommet().size() - 1));
            System.out.println("🚛 Centre de traitement : Point de départ et d'arrivée");
            System.out.println();

            String depot = "0";
            Map<String, Integer> contenances = genererContenancesAleatoires(graphe.getSommet(), depot);
            int capaciteMax = 10;

            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("         ORGANISATION DES TOURNÉES DE COLLECTE");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println();
            System.out.println("ℹ️  Information :");
            System.out.println("   Chaque camion peut transporter jusqu'à " + capaciteMax + " unités de déchets.");
            System.out.println("   Plusieurs tournées sont nécessaires pour collecter");
            System.out.println("   tous les points de collecte.");
            System.out.println();

            List<ItineraireVoyageur> tournees = AlgorithmeMST.calculerItineraireMSTAvecCapacite(
                    graphe, depot, contenances, capaciteMax);

            System.out.println("\n═══════════════════════════════════════════════════════════");
            System.out.println("📋 PLANNING DES TOURNÉES : " + tournees.size() + " tournée(s)");
            System.out.println("═══════════════════════════════════════════════════════════\n");

            int distanceTotaleGlobale = 0;
            int tempsTotal = 0;

            for (int i = 0; i < tournees.size(); i++) {
                ItineraireVoyageur tournee = tournees.get(i);
                int chargeTournee = calculerChargeTournee(tournee.getChemin(), contenances, depot);
                double distanceKm = tournee.getTotaleDistance() / 1000.0;
                int tempsEstime = (int) (distanceKm * 3 + tournee.getChemin().size() * 5);

                System.out.println("🚛 TOURNÉE N°" + (i + 1));
                System.out.println("───────────────────────────────────────────────────────");
                System.out.println("   Charge : " + chargeTournee + "/" + capaciteMax + " unités");
                System.out.println("   Distance : " + String.format("%.2f", distanceKm) + " km");
                System.out.println("   Temps estimé : " + tempsEstime + " minutes");
                System.out.println("   Nombre d'arrêts : " + (tournee.getChemin().size() - 2));

                if (i < 3 || tournees.size() <= 4) {
                    System.out.print("   Parcours : ");
                    afficherParcoursCompact(tournee.getChemin(), depot);
                }

                System.out.println();
                distanceTotaleGlobale += tournee.getTotaleDistance();
                tempsTotal += tempsEstime;
            }

            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("📊 BILAN GLOBAL DE LA JOURNÉE");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println();
            System.out.println("   Distance totale parcourue : " + String.format("%.2f", distanceTotaleGlobale / 1000.0) + " km");
            System.out.println("   Temps total estimé : " + (tempsTotal / 60) + "h" + String.format("%02d", tempsTotal % 60));
            System.out.println("   Nombre de tournées : " + tournees.size());
            System.out.println();

            System.out.println("💡 Bon à savoir :");
            System.out.println("   ✓ Tous les points de collecte sont visités");
            System.out.println("   ✓ L'itinéraire est optimisé pour réduire les distances");
            System.out.println("   ✓ Chaque tournée respecte la capacité du camion");
            System.out.println();

        } catch (IOException e) {
            System.err.println("❌ Erreur : Impossible de charger les données.");
        }
    }

    private static void afficherParcoursCompact(List<String> chemin, String depot) {
        int compteur = 0;
        for (int i = 0; i < chemin.size(); i++) {
            String point = chemin.get(i);

            if (i == 0) {
                System.out.print("Dépôt");
            } else if (i == chemin.size() - 1) {
                System.out.print(" → Dépôt");
            } else {
                if (compteur < 5) {
                    System.out.print(" → P" + point);
                    compteur++;
                } else if (compteur == 5) {
                    System.out.print(" → ...");
                    compteur++;
                }
            }
        }
        System.out.println();
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
