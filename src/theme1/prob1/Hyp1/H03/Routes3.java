package theme1.prob1.Hyp1.H03;

import java.util.*;

public class Routes3 {

    //nombre de sommets du graphe
    private int n;

    // liste des sorties (pour avoir les adjacences)
    private List<List<Sortie3>> sortie;

    //constructeur
    public Routes3(int n) {
        this.n = n;
        //ArrayList de ArrayList (pour chaque sommet, il y a une liste des voisins adjacents)
        sortie = new ArrayList<>();
        //boucle pour initialiser tt les sommets avec liste vide
        for (int i = 0; i < n; i++) {
            sortie.add(new ArrayList<>());
        }
    }

    //methode pour ajouter une route sens unique
    public void ajouterRouteSensUnique (int u, int v, int p, int voies){
        sortie.get(u).add(new Sortie3(v,p,false,voies));
    }

    //methode pour ajouter une route double sens
    public void ajouterRouteDoublesens (int u, int v, int p, int voies) {
        // arrete de u vers v
        sortie.get(u).add(new Sortie3(v,p,true,voies));
        // arrete de v vers u
        sortie.get(v).add(new Sortie3(u,p,true,voies));
    }

    //Ajouter un sommet : une maison
    public int  ajouterMaison (){
        sortie.add(new ArrayList<>());
        //rajouter 1 au nb total de sommets
        n++;
        return n-1;
    }

    //comme n est un attribut privé, on le retourne avec une méthode
    public int taille (){
        return n;
    }

    //retourner les adjacents (pour utiliser ds Dijkstra)
    public List<List<Sortie3>> Adj() {
        return sortie;
    }
}
