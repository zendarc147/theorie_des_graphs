package src.theme1.prob1.Hyp1.H03;

import java.util.*;

public class Main {

    //Construire le chemin
    public static List<Integer> Chemin(int[] pred, int destination){
        List<Integer> chemin = new ArrayList<>();
        int act = destination;
        //tant que l'arrête de départ n'est pas atteinte, on prend le prédecesseur jusqu'à arriver à la source (chemin fait à l'envers)
        while(act!=-1){
            chemin.add(act);
            act=pred[act];
        }
        //chemin à l'envers donc on le remet à l'endroit
        Collections.reverse(chemin);
        // on retourne le chemin
        return chemin;
    }

    //relier la maison au graphe (H03)
    public static void maisonGraphe(Routes3 r, Maison3 m){

        int u = m.depart;
        int v = m.arrive;

        // verifier si la rue existe bien ds le graphe
        Sortie3 arc = null;
        for (Sortie3 s : r.Adj().get(u)){
            if (s.numr == v){
                arc = s;
                break;
            }
        }
        //si l'arête n'existe pas
        if (arc == null){
            System.out.println("La rue indique n'existe pas...");
            return;
        }
        //si double sens et 1 voie
        if (arc.doublesens && arc.voies == 1){
            r.ajouterRouteSensUnique(u,m.id,arc.poids,1);
            r.ajouterRouteSensUnique(m.id,v,arc.poids,1);
            r.ajouterRouteSensUnique(v,m.id,arc.poids,1);
            r.ajouterRouteSensUnique(m.id,u,arc.poids,1);
            return;
        }
        //si sens unique
        if(!arc.doublesens && arc.voies == 1){
            // coté non autorisé
            if(m.coteG){
                System.out.println("rue a sens unique, impossible de ramasser à gauche");
                //impossible de passer par la maison
                m.verif = 1;
                return;
            }
            //si à droite
            r.ajouterRouteSensUnique(u,m.id,arc.poids,1);
            r.ajouterRouteSensUnique(m.id,v,arc.poids,1);
            return;
        }

        //si double sens et 2 voies
        if (m.coteG){
            //si cote gauche alors le sens c'est v > maison > u
            r.ajouterRouteSensUnique(v,m.id,arc.poids,arc.voies);
            r.ajouterRouteSensUnique(m.id,u,arc.poids,arc.voies);
        }
        else {
            //si cote droit alors le sens c'est u > maison > v
            r.ajouterRouteSensUnique(u,m.id,arc.poids,arc.voies);
            r.ajouterRouteSensUnique(m.id,v,arc.poids,arc.voies);
        }
    }


    //main principal
    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

        //creation graphe pour test

        //25 sommets
        Routes3 gt = new Routes3(25);

        //on ajoute les arrêtes
        gt.ajouterRouteSensUnique(1, 2, 4,1);
        gt.ajouterRouteDoublesens(2, 3, 2,2);
        gt.ajouterRouteDoublesens(2, 6, 5,1);
        gt.ajouterRouteDoublesens(6, 5, 1,1);
        gt.ajouterRouteDoublesens(5, 4, 7,1);
        gt.ajouterRouteDoublesens(5, 12, 3,1);
        gt.ajouterRouteDoublesens(6, 7, 1,1);
        gt.ajouterRouteDoublesens(7, 8, 6,1);
        gt.ajouterRouteSensUnique(9, 8, 3,1);
        gt.ajouterRouteSensUnique(10, 9, 1,1);
        gt.ajouterRouteDoublesens(10, 11, 7,1);
        gt.ajouterRouteSensUnique(13, 5, 3,1);
        gt.ajouterRouteDoublesens(6, 14, 1,2);
        gt.ajouterRouteDoublesens(7, 16, 4,1);
        gt.ajouterRouteDoublesens(8, 17, 3,2);
        gt.ajouterRouteDoublesens(16, 17, 1,1);
        gt.ajouterRouteDoublesens(15, 16, 7,1);
        gt.ajouterRouteDoublesens(14, 15, 3,1);
        gt.ajouterRouteDoublesens(13, 14, 1,1);
        gt.ajouterRouteDoublesens(13, 21, 4,1);
        gt.ajouterRouteDoublesens(14, 20, 3,1);
        gt.ajouterRouteSensUnique(19, 15, 2,1);
        gt.ajouterRouteDoublesens(16, 19, 4,1);
        gt.ajouterRouteDoublesens(10, 18, 3,1);
        gt.ajouterRouteDoublesens(18, 19, 2,1);
        gt.ajouterRouteDoublesens(19, 20, 3,1);
        gt.ajouterRouteDoublesens(20, 21, 3,1);
        gt.ajouterRouteDoublesens(21, 22, 1,2);
        gt.ajouterRouteSensUnique(20, 23, 5,1);
        gt.ajouterRouteDoublesens(19, 24, 3,2);
        gt.ajouterRouteDoublesens(18, 0, 1,1);
        gt.ajouterRouteDoublesens(24, 0, 2,2);
        gt.ajouterRouteDoublesens(23, 24, 3,1);

        //sommet de départ du camion
        int depart = 1;

        // Demandes des clients (sommet -> maison)
        List<Maison3> demandes = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Client : demander un enlèvement");
            System.out.println("2. Entreprise : voir toutes les demandes");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");
            int choix = sc.nextInt();

            if (choix == 1) {
                System.out.print("Entrez le sommet de départ de la rue où se trouve votre maison : ");
                int departMaison = sc.nextInt();
                System.out.print("Entrez le sommet d'arrivée de la rue : ");
                int arriveeMaison = sc.nextInt();
                System.out.print("Votre maison est-elle du côté gauche ? (true/false) : ");
                boolean coteGauche = sc.nextBoolean();

                //creer et ajouter la maison
                Maison3 m = new Maison3(departMaison, arriveeMaison, coteGauche);
                //sommet
                m.id = gt.ajouterMaison();
                maisonGraphe(gt, m);
                demandes.add(m);

                //calcul chemin avec Dijkstra
                DijkstraDonnees3 chem = Dijkstra3.calculpcc(gt, depart);
                List<Integer> chemin = Chemin(chem.pred, m.id);

                if(m.verif !=1){
                    System.out.println("Chemin le plus court : " + chemin);
                    System.out.println("Distance totale : " + chem.distance[m.id] + " unités");
                }

            } else if (choix == 2) {

                if (demandes.isEmpty()) {
                    System.out.println("Aucune demande pour le moment.");
                    continue;
                }

                System.out.println("\n--- Liste des demandes ---");

                //calcul du chemin
                DijkstraDonnees3 chem = Dijkstra3.calculpcc(gt, depart);

                for (Maison3 m : demandes) {
                    List<Integer> chemin = Chemin(chem.pred,m.id);

                    System.out.println("Maison sur " + m.depart + "->" + m.arrive + ", cote : " + (m.coteG?"gauche":"droit"));
                    if(m.verif !=1){
                        System.out.println("Chemin à emprunter : " +chemin);
                        System.out.println("Distance : " +chem.distance[m.id] + " unites\n");
                    }
                    else{
                        System.out.println("Impossible car mauvais cote\n");
                    }
                }
            } else if (choix == 3) {
                System.out.println("Au revoir !");
                break;
            }
        }
    }
}
