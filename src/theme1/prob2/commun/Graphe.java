package theme1.prob2.commun;

import java.util.*;

public abstract class Graphe {
    protected int nbSommets;
    protected Map<Integer, List<Integer>> listeAdjacence;

    public Graphe(int nbSommets) {
        this.nbSommets = nbSommets;
        this.listeAdjacence = new HashMap<>();
        for (int i = 0; i < nbSommets; i++) {
            listeAdjacence.put(i, new ArrayList<>());
        }
    }

    public int getNbSommets() {
        return nbSommets;
    }

    public List<Integer> getVoisins(int sommet) {
        return listeAdjacence.get(sommet);
    }

    public Map<Integer, List<Integer>> getListeAdjacence() {
        return listeAdjacence;
    }

    public abstract void ajouterArete(int sommet1, int sommet2);
    public abstract void afficherGraphe();
}
