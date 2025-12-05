package theme1.prob2.HO2;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ChargeurGraphe {

    public static GrapheOriente chargerDepuisFichier(String cheminFichier) throws IOException {
        List<String> lignes = Files.readAllLines(Paths.get(cheminFichier));

        int nbSommets = 0;
        GrapheOriente graphe = null;
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
                graphe = new GrapheOriente(nbSommets);
                System.out.println("Nombre de sommets : " + nbSommets);
            }
            else if (parties[0].equals("RUE_ORIENTEE") && graphe != null) {
                int sommetDepart = Integer.parseInt(parties[1]);
                int sommetArrivee = Integer.parseInt(parties[2]);
                String nomRue = parties.length > 3 ? parties[3] : "Rue-" + sommetDepart + "-" + sommetArrivee;
                graphe.ajouterArete(sommetDepart, sommetArrivee);
                nomsRues.put(sommetDepart + "->" + sommetArrivee, nomsRues.size());
                System.out.println("  Ajout de la rue orientée : " + nomRue + " (" + sommetDepart + " → " + sommetArrivee + ")");
            }
            else if (parties[0].equals("RUE") && graphe != null) {
                System.out.println("  ⚠ Rue non orientée détectée (non supportée dans HO2) : ligne ignorée");
            }
            else if (parties[0].equals("RUE_DOUBLE") && graphe != null) {
                System.out.println("  ⚠ Rue double détectée (non supportée dans HO2 cas idéal) : ligne ignorée");
            }
        }

        if (graphe == null) {
            throw new IOException("Format de fichier invalide : SOMMETS: non trouvé");
        }

        return graphe;
    }
}
