package src.theme1.prob1.Hyp2.H01;

import java.util.*;

public class Ramassage1 {

    private Graphe1 graphe;
    //id des maisons à ramasser (des particuliers)
    private List<Integer> part;
    //le centre de traitement
    private int centre;
    //pour stocker la meilleur permutation
    private List<Integer> mc;

    //constructeur
    public Ramassage1(Graphe1 graphe, List<Integer> part, int centre){
        this.graphe = graphe;
        this.part = part;
        this.centre = centre;
    }

    //Algo TSP : distance minimale pour la tournée en testant les permutations
    public int tspDistance(){
        //on ne doit pas permutter le centre, seulement les maisons
        List<Integer> permutable = new ArrayList<>(part);
        List<List<Integer>> tt = new ArrayList<>();
        permuter(permutable,0,tt);

        /*
        //debut au centre de traitement
        noeud.add(0,centre);
        //fin au centre de traitement
        noeud.add(centre);

        //on veut seulement toucher aux maisons (ne pas changer les premier et dernier index qui sont le centre)
        List<List<Integer>> perm = new ArrayList<>();
        //permuter seulement entre index 1 (inclus) et inndex n-1 (inclus)
        permuter(noeud.subList(1,noeud.size()-1), 0, perm);*/
        //distance min est initialisée a l'infini

        int meilleur = Integer.MAX_VALUE;
        List<Integer> meilleurChemin = null;

        //boucle des permutations pour trouver la plus petite distance min
        for (List<Integer> p : tt) {
            int distance =0;

            //centre à la premiere maison
            distance+=distanceEntre(centre, p.get(0));

            //maisons ds l'ordre
            for (int i =0; i<p.size()-1;i++){
                distance += distanceEntre(p.get(i),p.get(i+1));
            }

            //derniere maison au centre
            distance+=distanceEntre(p.get(p.size()-1),centre);

            //on prend la plus petite distance
            if (distance < meilleur){
                meilleur = distance;

                meilleurChemin = new ArrayList<>();
                meilleurChemin.add(centre);
                meilleurChemin.addAll(p);
                meilleurChemin.add(centre);
            }
        }
        //stocker la meilleur permutation
        this.mc = meilleurChemin;

        //retourner la distance minimum
        return meilleur;
    }
    //tsp pour avoir le chemin
    public List<Integer> tspChemin(){
        if(mc==null){
            //si pas encore fait calculer la distance
            tspDistance();
        }
        //retourner le chemin
        return new ArrayList<>(mc);
    }

    //Dijkstra
    private int distanceEntre(int source,int destination){
        int n = graphe.nbsommets();

        //tableau des distances depuis la source
        int[] d = new int[n];
        Arrays.fill(d,Integer.MAX_VALUE);
        d[source]=0;

        // file de prioritee pour prendre le sommet le plus proche non visité
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        //on part de la source
        pq.add(new int[]{source,0});
        while (!pq.isEmpty()){
            //on prend ds la file le prochain element a traiter
            int[] cur = pq.poll();
            //num du sommet
            int u = cur[0];
            //distance jusqu'au sommet
            int distU = cur[1];
            //si distance est pas la plus petite on passe
            if (distU>d[u]){
                continue;
            }
            //on parcours les voisins
            for (Arete1 a : graphe.getVoisins(u)){
                int v =a.destination;
                //nouvelle distance (avec v la distance déjà parcourue du centre jusqu'a u)
                int dN = distU + a.distance;
                //
                if (dN<d[v]){
                    d[v] = dN;
                    pq.add(new int[]{v,dN});
                }
            }
        }
        //retourner la distance
        return d[destination];
    }
    //générer les permutations possibles (TPS)
    private void permuter(List<Integer> m, int p, List<List<Integer>> perm){
        //si la permutation est terminée
        if (p == m.size()-1){
            //on copie les permutations pour mettre dans la liste des permutations
            perm.add(new ArrayList<>(m));
            return;
        }
        //permuter l'element a la position p avec tt les autres
        for (int i = p; i < m.size(); i++){
            //echanger m[p] et m[i]
            Collections.swap(m,p,i);
            //recursif pour permutter la suite
            permuter(m,p+1,perm);
            //revenir aux positions de départ
            Collections.swap(m,p,i);
        }
    }
}
