package src.theme1.prob1.Hyp1.H01;

import java.util.ArrayList;
import java.util.List;

public class Routes1 {
    //nombre de sommets du graphe
    private int n;

    // liste des sorties (pour avoir les adjacences)
    private List<List<Sortie1>> sortie;

    //constructeur
    public Routes1(int n) {
        this.n = n;
        //ArrayList de ArrayList (pour chaque sommet, il y a une liste des voisins adjacents)
        sortie = new ArrayList<>();
        //boucle pour initialiser tt les sommets avec liste vide
        for (int i = 0; i < n; i++) {
            sortie.add(new ArrayList<>());
        }
    }

    //methode pour ajouter une route double sens
    public void ajouterRoute (int u, int v, int p) {
        //HO1 : rue à double sens
        // arrete de u vers v
        sortie.get(u).add(new Sortie1(v,p));
        // arrete de v vers u
        sortie.get(v).add(new Sortie1(u,p));
    }

    //comme n est un attribut privé, on le retourne avec une méthode
    public int taille (){
        return n;
    }

    //retourner les adjacents (pour utiliser ds Dijkstra)
    public List<List<Sortie1>> Adj() {
        return sortie;
    }
}
