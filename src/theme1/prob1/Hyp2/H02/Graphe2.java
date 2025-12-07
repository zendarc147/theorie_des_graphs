package src.theme1.prob1.Hyp2.H02;

import java.util.ArrayList;
import java.util.List;

public class Graphe2 {


    //nombre de sommets (ou de noeuds / intersections)
    private int nb;
    //liste d'adjacence
    private List<List<Arete2>> adj;

    //constructeur
    public Graphe2(int nb) {
        this.nb = nb;
        adj = new ArrayList<>();
        for (int i = 0; i < nb; i++) {
            adj.add(new ArrayList<>());
        }
    }
    //ajouter arête non orientée
    public void ajouterA(int u, int v, int distance) {
        //mettre dans les 2 sens car non orienté : H01
        adj.get(u).add(new Arete2(v, distance, true));
        adj.get(v).add(new Arete2(u, distance,true));
    }

    //ajouter arête orientée
    public void ajouterAo(int u, int v, int distance) {
        //arete uniquement de u vers v (un seul sens) : H02
        adj.get(u).add(new Arete2(v, distance,false));
    }

    //trouver les voisins
    public List<Arete2> getVoisins(int u){
        //trouver les arêtes adjacentes
        return adj.get(u);
    }
    //retourner le nb de sommets
    public int nbsommets(){
        return nb;
    }
}
