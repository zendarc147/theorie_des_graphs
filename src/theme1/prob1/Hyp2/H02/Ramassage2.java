package theme1.prob1.Hyp2.H02;

import java.util.*;

public class Ramassage2 {

    private Graphe2 graphe;
    //liste des maisons à ramasser (des particuliers)
    private List<Maison2> part;
    //le centre de traitement
    private int centre;
    //pour stocker la meilleur permutation
    private List<Integer> mc;

    //constructeur
    public Ramassage2(Graphe2 graphe, List<Maison2> part, int centre){
        this.graphe = graphe;
        this.part = part;
        this.centre = centre;
    }

    //Algo TSP : distance minimale pour la tournée en testant les permutations
    public int tspDistance(){
        // que les maisons ramassables
        List<Maison2> maisonRamassable = new ArrayList<>();
        for(Maison2 m : part) {
            //seulement celles qui n'ont pas encore été ramassée
            if (!m.verif) {
                maisonRamassable.add(m);
            }
        }
        //si pas de maison à ramasser
        if(maisonRamassable.isEmpty()){
            return 0;
        }

        //on ne doit pas permutter le centre, seulement les maisons
        List<Integer> sommets = new ArrayList<>();
        for(Maison2 m : maisonRamassable){
            sommets.add(m.depart);
        }
        List<List<Integer>> tt = new ArrayList<>();
        permuter(sommets,0,tt);

        int meilleur = Integer.MAX_VALUE;
        List<Integer> meilleurChemin = null;

        //boucle des permutations pour trouver la plus petite distance min
        for (List<Integer> p : tt) {
            int distance =0;
            //verifie si le chemin existe
            boolean cheminVal =true;

            //centre à la premiere maison
            int dist =distanceEntre(centre, p.get(0));
            if(dist == Integer.MAX_VALUE){
                cheminVal = false;
            }else{
                distance += dist;
            }

            //maisons ds l'ordre
            for (int i =0; i<p.size()-1 && cheminVal;i++){
                if(dist == Integer.MAX_VALUE){
                    cheminVal = false;
                }else{
                    distance += dist;
                }
            }

            //derniere maison au centre
            if (cheminVal){
                dist = distanceEntre(p.get(p.size()-1), centre);
                if(dist == Integer.MAX_VALUE){
                    cheminVal = false;
                }else{
                    distance += dist;
                }
            }

            //on prend la plus petite distance
            if (cheminVal && distance < meilleur){
                meilleur = distance;

                meilleurChemin = new ArrayList<>();
                meilleurChemin.add(centre);
                meilleurChemin.addAll(p);
                meilleurChemin.add(centre);
            }
        }
        //stocker la meilleur permutation
        // si aucune permutation valide, on met mc à une liste vide pour éviter NullPointerException
        if (meilleurChemin == null) {
            mc = new ArrayList<>();
        } else {
            mc = meilleurChemin;
        }

        //retourner la distance minimum
        return (meilleurChemin == null) ? 0 : meilleur;
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
    //verifier si maison est ramassable ou non en fct du sens de la rue
    public boolean maisonR(Maison2 m){
        //verifier si l'arete existe ds le bon sens (depart à arrivee)
        for(Arete2 a : graphe.getVoisins(m.depart)){
            if(a.destination == m.arrivee) {
                if (!m.coteG) {
                    //maison ramassable si cote droit
                    return true;
                }
                //si l'arete est double sens
                if (a.doubleSens) {
                    return true;
                }
                //si coté gauche sur arete oriente : impossible
                return false;
            }
        }
        //si double sens
        if(m.coteG) {
            for (Arete2 a : graphe.getVoisins(m.arrivee)) {
                if (a.destination == m.depart && a.doubleSens) {
                    return true;
                }
            }
        }
        //sinon impossible
        return false;
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
            for (Arete2 a : graphe.getVoisins(u)){
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
