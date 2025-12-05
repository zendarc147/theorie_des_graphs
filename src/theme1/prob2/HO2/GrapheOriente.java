package theme1.prob2.HO2;

import java.util.*;

public class GrapheOriente {
    private int nbSommets;
    private Map<Integer, List<Integer>> listeAdjacence;
    private Map<Integer, Integer> degresEntrants;
    private Map<Integer, Integer> degresSortants;

    public GrapheOriente(int nbSommets) {
        this.nbSommets = nbSommets;
        this.listeAdjacence = new HashMap<>();
        this.degresEntrants = new HashMap<>();
        this.degresSortants = new HashMap<>();

        for (int i = 0; i < nbSommets; i++) {
            listeAdjacence.put(i, new ArrayList<>());
            degresEntrants.put(i, 0);
            degresSortants.put(i, 0);
        }
    }

    public void ajouterArete(int sommetDepart, int sommetArrivee) {
        listeAdjacence.get(sommetDepart).add(sommetArrivee);
        degresSortants.put(sommetDepart, degresSortants.get(sommetDepart) + 1);
        degresEntrants.put(sommetArrivee, degresEntrants.get(sommetArrivee) + 1);
    }

    public int getDegreeEntrant(int sommet) {
        return degresEntrants.get(sommet);
    }

    public int getDegreeSortant(int sommet) {
        return degresSortants.get(sommet);
    }

    public List<Integer> getVoisins(int sommet) {
        return listeAdjacence.get(sommet);
    }

    public int getNbSommets() {
        return nbSommets;
    }

    public Map<Integer, List<Integer>> getListeAdjacence() {
        return listeAdjacence;
    }

    public void afficherGraphe() {
        System.out.println("\n=== Structure du graphe orienté ===");
        for (int i = 0; i < nbSommets; i++) {
            System.out.print("Sommet " + i + " (deg_in=" + degresEntrants.get(i)
                           + ", deg_out=" + degresSortants.get(i) + ") : ");
            System.out.print("successeurs = " + listeAdjacence.get(i));
            System.out.println();
        }
    }
}
