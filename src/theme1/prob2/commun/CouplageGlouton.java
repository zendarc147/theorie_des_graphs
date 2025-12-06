package theme1.prob2.commun;

import java.util.*;

/**
 * Algorithme de couplage glouton pour le problème du postier chinois
 *
 * Trouve un couplage (pas forcément optimal) entre sommets impairs
 * en appariant les sommets les plus proches.
 */
public class CouplageGlouton {

    /**
     * Représente une paire de sommets appariés
     */
    public static class Paire {
        public int sommet1;
        public int sommet2;
        public int distance;

        public Paire(int s1, int s2, int dist) {
            this.sommet1 = s1;
            this.sommet2 = s2;
            this.distance = dist;
        }

        @Override
        public String toString() {
            return "(" + sommet1 + " ↔ " + sommet2 + ", dist=" + distance + ")";
        }
    }

    /**
     * Trouve un couplage glouton entre sommets impairs
     *
     * Algorithme :
     * 1. Créer toutes les paires possibles avec leurs distances
     * 2. Trier les paires par distance croissante
     * 3. Sélectionner goulûment les paires (plus courte distance en premier)
     *    sans réutiliser un sommet déjà apparié
     *
     * Complexité : O(n² log n) où n = nombre de sommets impairs
     *
     * @param sommetsImpairs Liste des sommets de degrés impairs
     * @param distances Matrice des distances entre ces sommets
     * @return Liste de paires (couplage)
     */
    public static List<Paire> trouverCouplage(List<Integer> sommetsImpairs, int[][] distances) {
        int n = sommetsImpairs.size();

        // Créer toutes les paires possibles
        List<Paire> toutesPaires = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                toutesPaires.add(new Paire(
                    sommetsImpairs.get(i),
                    sommetsImpairs.get(j),
                    distances[i][j]
                ));
            }
        }

        // Trier par distance croissante
        toutesPaires.sort(Comparator.comparingInt(p -> p.distance));

        // Sélection gloutonne
        List<Paire> couplage = new ArrayList<>();
        Set<Integer> sommetsApparies = new HashSet<>();

        for (Paire paire : toutesPaires) {
            // Si aucun des deux sommets n'est déjà apparié
            if (!sommetsApparies.contains(paire.sommet1) &&
                !sommetsApparies.contains(paire.sommet2)) {

                couplage.add(paire);
                sommetsApparies.add(paire.sommet1);
                sommetsApparies.add(paire.sommet2);

                // Si tous les sommets sont appariés, on arrête
                if (sommetsApparies.size() == n) {
                    break;
                }
            }
        }

        return couplage;
    }

    /**
     * Calcule le coût total d'un couplage
     */
    public static int calculerCout(List<Paire> couplage) {
        int cout = 0;
        for (Paire paire : couplage) {
            cout += paire.distance;
        }
        return cout;
    }
}
