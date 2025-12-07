package theme1.prob1.Hyp2.H01;

import java.util.*;

public class Graphe1 {

    //nombre de sommets (ou de noeuds / intersections)
    private int nb;
    //liste d'adjacence
    private List<List<Arete1>> adj;

    //constructeur
    public Graphe1(int nb) {
        this.nb = nb;
        adj = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            //initialiser chaque sommet avec une liste vide
            adj.add(new ArrayList<>());
        }
    }
    //ajouter arête non orientée
    public void ajouterA(int u, int v, int distance) {
        //mettre dans les 2 sens car non orienté : H01
        adj.get(u).add(new Arete1(v, distance));
        adj.get(v).add(new Arete1(u, distance));
    }

    //trouver les voisins
    public List<Arete1> getVoisins(int u){
        //trouver les arêtes adjacentes
        return adj.get(u);
    }
    //retourner le nb de sommets
    public int nbsommets(){
        return nb;
    }
}
