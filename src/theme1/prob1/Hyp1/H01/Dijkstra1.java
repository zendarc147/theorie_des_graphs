package src.theme1.prob1.Hyp1.H01;

import java.util.*;

public class Dijkstra1 {
    //méthode pour caculer le plus court chemin en fonction des données
    public static DijkstraDonnees1 calculpcc(Routes1 routes, int source) {

        //recup du nb total de sommets du graphe
        int n = routes.taille();

        //initialisations.
        //pour chaque sommet(n sommets): distance min entre source et sommet
        int[] distance = new int[n];
        //pour chaque sommet(n sommets): pred du sommet
        int[] pred = new int[n];

        //pour savoir si on est déjà passé par un sommet ou non
        boolean[] visite = new boolean[n];

        //mettre les distances a infini (sauf celle de la source à 0) et la source à -1 pour les predecesseurs
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;
        Arrays.fill(pred, -1);
        //file de priorité (avec indice du sommet et sa distance depuis la source): donne tjrs le sommet avec la plus petite distance
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{source, 0});

        //boucle principale
        while (!pq.isEmpty()) {
            int[] element = pq.poll();
            int u = element[0];
            //si le sommet est visité alors on continue le chemin sans le re-marquer
            if(visite[u]) continue;
            // sinon on marque le sommet
            visite[u] = true;
            for(Sortie1 s : routes.Adj().get(u)){
                int v = s.numr;
                int t = s.poids;
                //pas de distance negatives
                if(t<0){
                    throw new IllegalArgumentException("les distances ne peuvent pas être négatives");
                }
                //si pas encore visité et que u n'est pas à une distance infinie et que la distance en passant par u pour aller à v est plus courte que le chemin actuel pour aller à v
                if(!visite[v] && distance[u] != Integer.MAX_VALUE && distance[u] + t< distance[v]) {
                    distance[v] = distance[u] + t;
                    pred[v] = u;
                    pq.add(new int[]{v, distance[v]});
                }
            }
        }
        //on renvoie les resultat de l'algo
        return new DijkstraDonnees1(distance, pred);
    }
}
