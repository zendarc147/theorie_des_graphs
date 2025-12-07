package src.theme1.prob1.Hyp1.H01;

import java.util.*;

public class Dijkstra1 {
    //méthode principale pour caculer le plus court chemin en fonction des données (Dijkstra)
    public static DijkstraDonnees1 calculpcc(Routes1 routes, int source) {

        //recup du nb total de sommets du graphe
        int n = routes.taille();

        //initialisations.
        //tableau pour chaque sommet(n sommets): distance min entre source et sommet
        int[] distance = new int[n];
        //tableau pour chaque sommet(n sommets): pred du sommet
        int[] pred = new int[n];

        //pour savoir si on est déjà passé par un sommet ou non
        boolean[] visite = new boolean[n];

        //mettre les distances a infini (sauf celle de la source à 0) et tt les predecesseurs à -1
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;
        Arrays.fill(pred, -1);
        //file de priorité (avec indice du sommet et sa distance depuis la source): donne tjrs le sommet avec la plus petite distance
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        //on ajoute la source ds la file
        pq.add(new int[]{source, 0});

        //boucle principale Dijkstra
        //tant que la file n'est pas vide
        while (!pq.isEmpty()) {
            //retirer le sommet avec la plus petite distance
            int[] element = pq.poll();
            int u = element[0];
            //si le sommet est visité alors on continue le chemin sans le re-marquer
            if(visite[u]) continue;
            // sinon on marque le sommet
            visite[u] = true;

            //on parcourt tt les voisins du sommet
            for(Sortie1 s : routes.Adj().get(u)){
                //sommet voisin
                int v = s.numr;
                //poids arete
                int t = s.poids;
                //pas de distance negatives
                if(t<0){
                    throw new IllegalArgumentException("les distances ne peuvent pas être négatives");
                }
                //si pas encore visité et que u n'est pas à une distance infinie et que la distance en passant par u pour aller à v est plus courte que le chemin actuel pour aller à v
                // donc si meilleur chemin est trouve
                if(!visite[v] && distance[u] != Integer.MAX_VALUE && distance[u] + t< distance[v]) {
                    //mettre à jour la distance
                    distance[v] = distance[u] + t;
                    //mettre à jour le poids
                    pred[v] = u;
                    //ajouter le sommet ds la file
                    pq.add(new int[]{v, distance[v]});
                }
            }
        }
        //on renvoie les resultat de l'algo
        return new DijkstraDonnees1(distance, pred);
    }
}
