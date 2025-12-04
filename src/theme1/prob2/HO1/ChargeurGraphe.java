package theme1.prob2.HO1;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ChargeurGraphe {

    public static GrapheNonOriente chargerDepuisFichier(String cheminFichier) throws IOException {
        List<String> lignes = Files.readAllLines(Paths.get(cheminFichier));

        int nbSommets = 0;
        GrapheNonOriente graphe = null;
        Map<String, Integer> nomsRues = new HashMap<>();

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
                graphe = new GrapheNonOriente(nbSommets);
                System.out.println("Nombre de sommets : " + nbSommets);
            }
            else if (parties[0].equals("RUE") && graphe != null) {
                int sommet1 = Integer.parseInt(parties[1]);
                int sommet2 = Integer.parseInt(parties[2]);
                String nomRue = parties.length > 3 ? parties[3] : "Rue-" + sommet1 + "-" + sommet2;
                graphe.ajouterArete(sommet1, sommet2);
                nomsRues.put(sommet1 + "-" + sommet2, nomsRues.size());
                System.out.println("  Ajout de la rue : " + nomRue + " (" + sommet1 + " ↔ " + sommet2 + ")");
            }
            else if (parties[0].equals("RUE_ORIENTEE") && graphe != null) {
                System.out.println("  ⚠ Rue orientée détectée (non supportée dans HO1) : ligne ignorée");
            }
            else if (parties[0].equals("RUE_DOUBLE") && graphe != null) {
                System.out.println("  ⚠ Rue double détectée (non supportée dans HO1) : ligne ignorée");
            }
        }

        if (graphe == null) {
            throw new IOException("Format de fichier invalide : SOMMETS: non trouvé");
        }

        return graphe;
    }
}
