package theme1.prob2.HO1;

import java.util.*;

public class GrapheNonOriente {
    private int nbSommets;
    private Map<Integer, List<Integer>> listeAdjacence;

    public GrapheNonOriente(int nbSommets) {
        this.nbSommets = nbSommets;
        this.listeAdjacence = new HashMap<>();
        for (int i = 0; i < nbSommets; i++) {
            listeAdjacence.put(i, new ArrayList<>());
        }
    }

    public void ajouterArete(int sommet1, int sommet2) {
        listeAdjacence.get(sommet1).add(sommet2);
        listeAdjacence.get(sommet2).add(sommet1);
    }

    public int getDegre(int sommet) {
        return listeAdjacence.get(sommet).size();
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
        System.out.println("\n=== Structure du graphe ===");
        for (int i = 0; i < nbSommets; i++) {
            System.out.print("Sommet " + i + " (degré " + getDegre(i) + ") : ");
            System.out.println(listeAdjacence.get(i));
        }
    }
}
