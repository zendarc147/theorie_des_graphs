package src.theme1.prob1.Hyp1.H02;

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

    //main principal
    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

        //creation graphe pour test
        //25 sommets
        Routes2 gt = new Routes2(25);
        //on ajoute les arrêtes
        gt.ajouterRouteSensUnique(1, 2, 4);
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
        gt.ajouterRouteDoublesens(18, 0, 1);
        gt.ajouterRouteDoublesens(24, 0, 2);
        gt.ajouterRouteDoublesens(23, 24, 3);

        //sommet de départ du camion
        int depart = 1;

        // Demandes des clients (sommet -> maison)
        List<Integer> demandes = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Client : demander un enlèvement");
            System.out.println("2. Entreprise : voir toutes les demandes");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");
            int choix = sc.nextInt();

            if (choix == 1) {
                System.out.print("Entrez le numéro de votre maison (0 à 25) : ");
                int maison = sc.nextInt();
                demandes.add(maison);

                DijkstraDonnees2 res = Dijkstra2.calculpcc(gt, depart);
                List<Integer> chemin = Chemin(res.pred, maison);

                System.out.println("Chemin le plus court : " + chemin);
                System.out.println("Distance totale : " + res.distance[maison] + " unités");
            } else if (choix == 2) {

                if (demandes.isEmpty()) {
                    System.out.println("Aucune demande pour le moment.");
                    continue;
                }

                System.out.println("\n--- Liste des demandes ---");

                DijkstraDonnees2 res = Dijkstra2.calculpcc(gt, depart);

                for (int d : demandes) {
                    List<Integer> chemin = Chemin(res.pred, d);
                    System.out.println("Maison " + d + " : chemin " + chemin +
                            " (distance " + res.distance[d] + ")");
                }

            } else if (choix == 3) {
                System.out.println("Au revoir !");
                break;
            }
        }
    }
}
