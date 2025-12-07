package src.theme1.prob1.Hyp1.H02;

import java.util.*;

public class Routes2 {
    //nombre de sommets du graphe
    private int n;

    // liste des sorties (pour avoir les adjacences)
    private List<List<Sortie2>> sortie;

    //constructeur
    public Routes2(int n) {
        this.n = n;
        //ArrayList de ArrayList (pour chaque sommet de la liste, il y a une liste des voisins adjacents)
        //initialisé la liste des sommets (externe) vide
        sortie = new ArrayList<>();
        //boucle pour initialiser tt les sommets avec liste vide (listes interne)
        for (int i = 0; i < n; i++) {
            sortie.add(new ArrayList<>());
        }
    }

    //methode pour ajouter une route sens unique
    public void ajouterRouteSensUnique (int u, int v, int p){
        sortie.get(u).add(new Sortie2(v,p,false,1));
    }

    //methode pour ajouter une route double sens
    public void ajouterRouteDoublesens (int u, int v, int p) {
        // arrete de u vers v
        sortie.get(u).add(new Sortie2(v,p,true,2));
        // arrete de v vers u
        sortie.get(v).add(new Sortie2(u,p,true,2));
    }

    public int ajouterMaison(){
        //liste vide pour la nouvelle maison
        sortie.add(new ArrayList<>());
        n++;
        //donner indice de la maison (n-1 car indices commencent à 0)
        return n-1;
    }

    //comme n est un attribut privé, on le retourne avec une méthode
    public int taille (){
        return n;
    }

    //retourner les adjacents (pour utiliser ds Dijkstra)
    public List<List<Sortie2>> Adj() {
        return sortie;
    }
}
