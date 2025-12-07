package theme1.prob1.Hyp1.H01;
import java.util.*;

public class Main11 {

    //Construire le chemin (grace au tableau des predecesseurs)
    public static List<Integer> Chemin(int[] pred, int destination){
        //liste pour stocker les sommets du chemin
        List<Integer> chemin = new ArrayList<>();
        // act c'est le sommet actuel
        int act = destination;
        //tant que l'arrête de départ n'est pas atteinte, on prend le prédecesseur jusqu'à arriver à la source (chemin fait à l'envers)
        while(act!=-1){
            //ajouter le sommet actuel a la liste
            chemin.add(act);
            //remonter au predecesseur
            act=pred[act];
        }

        //chemin à l'envers donc on le remet à l'endroit
        Collections.reverse(chemin);

        // on retourne le chemin
        return chemin;
    }

    /* Pour mettre le graphe ds un fichier texte

    public static Routes chargerFichier(String nom) throws IOException {
        Scanner sc = new Scanner(new File(nom));
        int n = sc.nextInt();
        Routes g = new Routes(n);

        while (sc.hasNextInt()) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int p = sc.nextInt();
            g.ajouterRoute(u, v, p);
        }
        return g;
    }
    */

    //main principal
    public static void main(String[] args) {

        //scanner pour lire les interactions
        Scanner sc = new Scanner (System.in);

        //creation graphe pour test

        //25 sommets
        Routes1 gt = new Routes1(25);

        //on ajoute les arrêtes
        gt.ajouterRoute(1, 2, 4);
        gt.ajouterRoute(2, 3, 2);
        gt.ajouterRoute(2, 6, 5);
        gt.ajouterRoute(6, 5, 1);
        gt.ajouterRoute(5, 4, 7);
        gt.ajouterRoute(5, 12, 3);
        gt.ajouterRoute(6, 7, 1);
        gt.ajouterRoute(7, 8, 6);
        gt.ajouterRoute(8, 9, 3);
        gt.ajouterRoute(9, 10, 1);
        gt.ajouterRoute(10, 11, 7);
        gt.ajouterRoute(5, 13, 3);
        gt.ajouterRoute(6, 14, 1);
        gt.ajouterRoute(7, 16, 4);
        gt.ajouterRoute(8, 17, 3);
        gt.ajouterRoute(16, 17, 1);
        gt.ajouterRoute(15, 16, 7);
        gt.ajouterRoute(14, 15, 3);
        gt.ajouterRoute(13, 14, 1);
        gt.ajouterRoute(13, 21, 4);
        gt.ajouterRoute(14, 20, 3);
        gt.ajouterRoute(15, 19, 2);
        gt.ajouterRoute(16, 19, 4);
        gt.ajouterRoute(10, 18, 3);
        gt.ajouterRoute(18, 19, 2);
        gt.ajouterRoute(19, 20, 3);
        gt.ajouterRoute(20, 21, 3);
        gt.ajouterRoute(21, 22, 1);
        gt.ajouterRoute(20, 23, 5);
        gt.ajouterRoute(19, 24, 3);
        gt.ajouterRoute(18, 0, 1);
        gt.ajouterRoute(24, 0, 2);
        gt.ajouterRoute(23, 24, 3);

        //sommet de départ du camion
        int depart = 1;

        // Demandes des clients (sommet, maison)
        List<Integer> demandes = new ArrayList<>();

        //menu theme 1 (aide de chatgpt)
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Demander un enlèvement");
            System.out.println("2. Voir toutes les demandes");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");
            int choix = sc.nextInt();

            if ( choix == 1) {
                System.out.print("Entrez le numéro de votre maison (0 à 25) : ");
                int maison = sc.nextInt();
                //sauvegarder la demande ds la liste
                demandes.add(maison);

                //faire Dijkstra a partir du centre de traitement
                DijkstraDonnees1 res = Dijkstra1.calculpcc(gt, depart);
                //le chemin aller
                List<Integer> chemin = Chemin(res.pred, maison);
                //le chemin retour
                List<Integer> cheminRetour = new ArrayList<>(chemin);
                Collections.reverse(cheminRetour);
                // memes distances
                int distanceA = res.distance[maison];
                int distanceR = distanceA;

                //chemin complet
                List<Integer> cheminComplet = new ArrayList<>(chemin);
                cheminComplet.addAll(cheminRetour.subList(1, cheminRetour.size()));

                System.out.println("Chemin le plus court : " + chemin);
                System.out.println("Distance totale : " + res.distance[maison] + " unités");
            } else if (choix == 2) {

                if (demandes.isEmpty()) {
                    System.out.println("Aucune demande pour le moment.");
                    //retourner au menu
                    continue;
                }

                System.out.println("\n--- Liste des demandes ---");

                // Dijkstra une seule fois prcq la c'est juste la liste des demandes donc on donne le chemin aller pour information mais pas besoin de faire retour ici
                DijkstraDonnees1 res = Dijkstra1.calculpcc(gt, depart);

                //pour chaque demande afficher chemin et distance
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
