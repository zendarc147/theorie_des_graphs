package theme2.approche1;

import java.io.*;

public class Mt2Citoyen {

    public static void main(String[] args) {
        try {
            GrapheNonOriente graphe = new GrapheNonOriente();
            graphe.chargerFichier("data/graphe_reel_theme2.txt");

            System.out.println("╔════════════════════════════════════════════════════════════╗");
            System.out.println("║     INFORMATION : Tournée de Collecte des Déchets         ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("📍 Nombre de points de collecte : " + (graphe.getSommet().size() - 1));
            System.out.println("🚛 Point de départ : Centre de traitement");
            System.out.println();

            String sommetDepart = "0";
            ItineraireVoyageur itineraire = AlgorithmePlusProcheVoisin.calculerItineraire(graphe, sommetDepart);

            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("           ITINÉRAIRE DU CAMION DE COLLECTE");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println();

            afficherItineraireSimple(itineraire);

            System.out.println("\n📊 RÉSUMÉ");
            System.out.println("─────────────────────────────────────────────────────────");
            double distanceKm = itineraire.getTotaleDistance() / 1000.0;
            System.out.println("Distance totale parcourue : " + String.format("%.2f", distanceKm) + " km");
            System.out.println("Nombre d'arrêts : " + (itineraire.getChemin().size() - 2));

            int tempsEstime = (int) (distanceKm * 3 + itineraire.getChemin().size() * 5);
            System.out.println("Temps estimé de la tournée : " + tempsEstime + " minutes");
            System.out.println();

            System.out.println("💡 Information utile :");
            System.out.println("   Le camion passe par tous les points de collecte");
            System.out.println("   en suivant l'itinéraire le plus court possible.");
            System.out.println();

        } catch (IOException e) {
            System.err.println("❌ Erreur : Impossible de charger les données.");
        }
    }

    private static void afficherItineraireSimple(ItineraireVoyageur itineraire) {
        int etape = 1;
        for (int i = 0; i < itineraire.getChemin().size(); i++) {
            String point = itineraire.getChemin().get(i);

            if (i == 0) {
                System.out.println("🏁 DÉPART : Centre de traitement");
            } else if (i == itineraire.getChemin().size() - 1) {
                System.out.println("🏁 RETOUR : Centre de traitement");
            } else {
                if (etape % 5 == 1) {
                    System.out.print("   ");
                }
                System.out.print("Point " + point);

                if (i < itineraire.getChemin().size() - 1) {
                    System.out.print(" → ");
                }

                if (etape % 5 == 0) {
                    System.out.println();
                }
                etape++;
            }
        }
        if ((etape - 1) % 5 != 0) {
            System.out.println();
        }
    }
}
