package theme1.prob2.commun;

import java.util.*;

public class Dijkstra {

    /**
     * Résultat de Dijkstra : distances et chemins
     */
    public static class ResultatDijkstra {
        public Map<Integer, Integer> distances;  // sommet → distance
        public Map<Integer, Integer> precedents; // sommet → prédécesseur

        public ResultatDijkstra() {
            this.distances = new HashMap<>();
            this.precedents = new HashMap<>();
        }
    }

    /**
     * Algorithme de Dijkstra depuis un sommet source
     * Trouve les plus courts chemins vers tous les autres sommets
     *
     * @param graphe Le graphe (liste d'adjacence)
     * @param nbSommets Nombre de sommets
     * @param source Sommet de départ
     * @return Résultat avec distances et chemins
     */
    public static ResultatDijkstra calculerPlusCourtsChemin(
            Map<Integer, List<Integer>> graphe,
            int nbSommets,
            int source) {

        ResultatDijkstra resultat = new ResultatDijkstra();

        // Initialisation
        for (int i = 0; i < nbSommets; i++) {
            resultat.distances.put(i, Integer.MAX_VALUE);
            resultat.precedents.put(i, -1);
        }
        resultat.distances.put(source, 0);

        // File de priorité (sommet, distance)
        PriorityQueue<int[]> file = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        file.offer(new int[]{source, 0});

        Set<Integer> visites = new HashSet<>();

        while (!file.isEmpty()) {
            int[] current = file.poll();
            int sommet = current[0];
            int distance = current[1];

            if (visites.contains(sommet)) {
                continue;
            }
            visites.add(sommet);

            // Parcourir les voisins
            List<Integer> voisins = graphe.get(sommet);
            if (voisins == null) continue;

            for (int voisin : voisins) {
                // Dans un graphe non pondéré, chaque arête a un poids de 1
                int nouvelleDistance = distance + 1;

                if (nouvelleDistance < resultat.distances.get(voisin)) {
                    resultat.distances.put(voisin, nouvelleDistance);
                    resultat.precedents.put(voisin, sommet);
                    file.offer(new int[]{voisin, nouvelleDistance});
                }
            }
        }

        return resultat;
    }

    /**
     * Reconstruit le chemin depuis la source jusqu'à la destination
     *
     * @param resultat Résultat de Dijkstra
     * @param destination Sommet d'arrivée
     * @return Liste des sommets formant le chemin
     */
    public static List<Integer> reconstruireChemin(ResultatDijkstra resultat, int destination) {
        List<Integer> chemin = new ArrayList<>();
        int current = destination;

        while (current != -1) {
            chemin.add(0, current);
            current = resultat.precedents.get(current);
        }

        return chemin;
    }

    /**
     * Calcule tous les plus courts chemins entre une liste de sommets
     *
     * @param graphe Le graphe
     * @param nbSommets Nombre de sommets
     * @param sommets Liste des sommets d'intérêt
     * @return Matrice de distances [i][j] = distance entre sommets[i] et sommets[j]
     */
    public static int[][] calculerMatriceDistances(
            Map<Integer, List<Integer>> graphe,
            int nbSommets,
            List<Integer> sommets) {

        int n = sommets.size();
        int[][] distances = new int[n][n];

        for (int i = 0; i < n; i++) {
            ResultatDijkstra resultat = calculerPlusCourtsChemin(graphe, nbSommets, sommets.get(i));
            for (int j = 0; j < n; j++) {
                distances[i][j] = resultat.distances.get(sommets.get(j));
            }
        }

        return distances;
    }
}
