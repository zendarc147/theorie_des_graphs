package src.theme1.prob1.Hyp1.H02;

import java.util.*;

public class Main12 {
    //Construire le chemin
    public static List<Integer> Chemin(int[] pred, int destination){
        //liste pour stocker le chemin
        List<Integer> chemin = new ArrayList<>();
        // act c'est le sommet actuel
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

    //relier la maison au graphe (H02)
    public static void maisonGraphe(Routes2 r, Maison2 m){

        int u = m.depart;
        int v = m.arrive;

        // verifier si la rue existe bien ds le graphe
        Sortie2 arc = null;
        for (Sortie2 s : r.Adj().get(u)){
            if (s.numr == v){
                //arete existe
                arc = s;
                break;
            }
        }
        //si l'arête n'existe pas
        if (arc == null){
            System.out.println("La rue indique n'existe pas...");
            //impossible de passer par la maison
            m.verif =1;
            return;
        }
        //si double sens (2 voies)
        if (arc.doublesens){
            if(m.coteG){
                r.ajouterRouteSensUnique(v,m.id,arc.poids);
                r.ajouterRouteSensUnique(m.id,u,arc.poids);
            }
            else{
                r.ajouterRouteSensUnique(u,m.id,arc.poids);
                r.ajouterRouteSensUnique(m.id,v,arc.poids);
            }
            return;
        }
        //si sens unique
        if(!arc.doublesens){
            // coté non autorisé
            if(m.coteG){
                System.out.println("rue a sens unique, impossible de ramasser à gauche");
                //impossible de passer par la maison
                m.verif = 1;
                return;
            }
            //si à droite
            r.ajouterRouteSensUnique(u,m.id,arc.poids);
            r.ajouterRouteSensUnique(m.id,v,arc.poids);
            return;
        }
    }

    //main principal
    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

        //creation graphe pour test
        //25 sommets
        Routes2 gt = new Routes2(25);

        //on ajoute les arrêtes
        gt.ajouterRouteDoublesens(1, 2, 4);
        gt.ajouterRouteDoublesens(2, 3, 2);
        gt.ajouterRouteDoublesens(2, 6, 5);
        gt.ajouterRouteDoublesens(6, 5, 1);
        gt.ajouterRouteDoublesens(5, 4, 7);
        gt.ajouterRouteDoublesens(5, 12, 3);
        gt.ajouterRouteDoublesens(6, 7, 1);
        gt.ajouterRouteDoublesens(7, 8, 6);
        gt.ajouterRouteSensUnique(9, 8, 3);
        gt.ajouterRouteSensUnique(10, 9, 1);
        gt.ajouterRouteDoublesens(10, 11, 7);
        gt.ajouterRouteSensUnique(13, 5, 3);
        gt.ajouterRouteDoublesens(6, 14, 1);
        gt.ajouterRouteDoublesens(7, 16, 4);
        gt.ajouterRouteDoublesens(8, 17, 3);
        gt.ajouterRouteDoublesens(16, 17, 1);
        gt.ajouterRouteDoublesens(15, 16, 7);
        gt.ajouterRouteDoublesens(14, 15, 3);
        gt.ajouterRouteDoublesens(13, 14, 1);
        gt.ajouterRouteDoublesens(13, 21, 4);
        gt.ajouterRouteDoublesens(14, 20, 3);
        gt.ajouterRouteSensUnique(19, 15, 2);
        gt.ajouterRouteDoublesens(16, 19, 4);
        gt.ajouterRouteDoublesens(10, 18, 3);
        gt.ajouterRouteDoublesens(18, 19, 2);
        gt.ajouterRouteDoublesens(19, 20, 3);
        gt.ajouterRouteDoublesens(20, 21, 3);
        gt.ajouterRouteDoublesens(21, 22, 1);
        gt.ajouterRouteSensUnique(20, 23, 5);
        gt.ajouterRouteDoublesens(19, 24, 3);
        gt.ajouterRouteSensUnique(18, 0, 1);
        gt.ajouterRouteDoublesens(24, 0, 2);
        gt.ajouterRouteDoublesens(23, 24, 3);

        //sommet de départ du camion
        int depart = 1;

        // Demandes des clients (sommet -> maison)
        List<Maison2> demandes = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Demander un enlèvement");
            System.out.println("2. Voir toutes les demandes");
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
                Maison2 m = new Maison2(departMaison, arriveeMaison, coteGauche);
                //sommet ajouté avec son indice
                m.id = gt.ajouterMaison();
                //relier maison au graphe
                maisonGraphe(gt, m);
                //ajouter à liste des demandes
                demandes.add(m);

                //calcul chemin avec Dijkstra
                DijkstraDonnees2 chem = Dijkstra2.calculpcc(gt, depart);
                List<Integer> chemin = Chemin(chem.pred, m.id);

                if(m.verif !=1){

                    int centre = depart;
                    //faire Dijkstra sur le retour : de la maison au centre
                    DijkstraDonnees2 chemRetour = Dijkstra2.calculpcc(gt, m.id);
                    //reconstitution chemin retour
                    List<Integer> cheminRetour = Chemin(chemRetour.pred,centre);

                    // Si aucun chemin
                    if (chemRetour.distance[centre] == Integer.MAX_VALUE) {
                        System.out.println("Impossible de retourner au centre depuis la maison");
                    }
                    //construire le chemin complet
                    List<Integer> cheminComplet = new ArrayList<>(chemin);
                    //on met le retour mais sans mettre la maison 2fois
                    cheminComplet.addAll(cheminRetour.subList(1, cheminRetour.size()));
                    System.out.println("Chemin à emprunter : " +cheminComplet);
                    System.out.println("Distance totale: " +(chem.distance[m.id] + chemRetour.distance[centre]) + " unites\n");
                }
                else{
                    System.out.println("Impossible car mauvais cote\n");
                }

            } else if (choix == 2) {

                if (demandes.isEmpty()) {
                    System.out.println("Aucune demande pour le moment.");
                    continue;
                }

                System.out.println("\n--- Liste des demandes ---");

                //calcul du chemin
                DijkstraDonnees2 chem = Dijkstra2.calculpcc(gt, depart);

                for (Maison2 m : demandes) {
                    List<Integer> chemin = Chemin(chem.pred,m.id);
                    System.out.println("Maison sur " + m.depart + "->" + m.arrive + ", cote : " + (m.coteG?"gauche":"droit"));

                    if(m.verif !=1){

                        int centre = depart;
                        //faire Dijkstra sur le retour : de la maison au centre
                        DijkstraDonnees2 chemRetour = Dijkstra2.calculpcc(gt, m.id);
                        //reconstitution chemin retour
                        List<Integer> cheminRetour = Chemin(chemRetour.pred,centre);

                        // Si aucun chemin
                        if (chemRetour.distance[centre] == Integer.MAX_VALUE) {
                            System.out.println("Impossible de retourner au centre depuis la maison");
                        }
                        //construire le chemin complet
                        List<Integer> cheminComplet = new ArrayList<>(chemin);
                        //on met le retour mais sans mettre la maison 2fois
                        cheminComplet.addAll(cheminRetour.subList(1, cheminRetour.size()));
                        System.out.println("Chemin à emprunter : " +cheminComplet);
                        System.out.println("Distance totale: " +(chem.distance[m.id] + chemRetour.distance[centre]) + " unites\n");
                    }
                    else{
                        System.out.println("Impossible car mauvais cote\n");
                    }
                }

            } else if (choix == 3) {
                System.out.println("Au revoir !");
                break;
            }
            else {
                System.out.println("Erreur, choix invalide");
            }
        }
        sc.close();
    }
}
