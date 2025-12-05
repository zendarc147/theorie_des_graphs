package theme1.prob2.HO3;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ChargeurGraphe {

    public static GrapheMixte chargerDepuisFichier(String cheminFichier) throws IOException {
        List<String> lignes = Files.readAllLines(Paths.get(cheminFichier));

        int nbSommets = 0;
        GrapheMixte graphe = null;

        System.out.println("\n=== Chargement du graphe depuis : " + cheminFichier + " ===");

        for (String ligne : lignes) {
            ligne = ligne.trim();

            // Ignorer les commentaires et lignes vides
            if (ligne.isEmpty() || ligne.startsWith("#")) {
                continue;
            }

            String[] parties = ligne.split("\\s+");

            if (parties[0].equals("SOMMETS:")) {
                nbSommets = Integer.parseInt(parties[1]);
                graphe = new GrapheMixte(nbSommets);
                System.out.println("Nombre de sommets : " + nbSommets);
            }
            else if (parties[0].equals("RUE") && graphe != null) {
                // RUE : double sens, 1 voie (ramassage des 2 côtés en 1 passage)
                int sommet1 = Integer.parseInt(parties[1]);
                int sommet2 = Integer.parseInt(parties[2]);
                String nomRue = parties.length > 3 ? parties[3] : "Rue-" + sommet1 + "-" + sommet2;
                graphe.ajouterAreteNonOrientee(sommet1, sommet2);
                System.out.println("  Ajout RUE (1 voie) : " + nomRue + " (" + sommet1 + " ↔ " + sommet2 + ")");
            }
            else if (parties[0].equals("RUE_DOUBLE") && graphe != null) {
                // RUE_DOUBLE : double sens, plusieurs voies (2 passages nécessaires)
                int sommet1 = Integer.parseInt(parties[1]);
                int sommet2 = Integer.parseInt(parties[2]);
                String nomRue = parties.length > 3 ? parties[3] : "Avenue-" + sommet1 + "-" + sommet2;
                graphe.ajouterAreteDouble(sommet1, sommet2);
                System.out.println("  Ajout RUE_DOUBLE (multi-voies) : " + nomRue + " (" + sommet1 + " ⇄ " + sommet2 + ")");
            }
            else if (parties[0].equals("RUE_ORIENTEE") && graphe != null) {
                // RUE_ORIENTEE : sens unique
                int sommetDepart = Integer.parseInt(parties[1]);
                int sommetArrivee = Integer.parseInt(parties[2]);
                String nomRue = parties.length > 3 ? parties[3] : "Sens-Unique-" + sommetDepart + "-" + sommetArrivee;
                graphe.ajouterAreteOrientee(sommetDepart, sommetArrivee);
                System.out.println("  Ajout RUE_ORIENTEE (sens unique) : " + nomRue + " (" + sommetDepart + " → " + sommetArrivee + ")");
            }
        }

        if (graphe == null) {
            throw new IOException("Format de fichier invalide : SOMMETS: non trouvé");
        }

        return graphe;
    }
}
