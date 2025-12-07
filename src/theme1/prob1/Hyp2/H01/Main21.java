package theme1.prob1.Hyp2.H01;

import java.util.*;

public class Main21 {

    public static void main(String[] args) {
        //creer le graphe
        Graphe1 g = new Graphe1(25);

        // ajouter les arêtes (rues) : H01 donc non orienté
        g.ajouterA(1, 2, 4);
        g.ajouterA(2, 3, 2);
        g.ajouterA(2, 6, 5);
        g.ajouterA(6, 5, 1);
        g.ajouterA(5, 4, 7);
        g.ajouterA(5, 12, 3);
        g.ajouterA(6, 7, 1);
        g.ajouterA(7, 8, 6);
        g.ajouterA(9, 8, 3);
        g.ajouterA(10, 9, 1);
        g.ajouterA(10, 11, 7);
        g.ajouterA(13, 5, 3);
        g.ajouterA(6, 14, 1);
        g.ajouterA(7, 16, 4);
        g.ajouterA(8, 17, 3);
        g.ajouterA(16, 17, 1);
        g.ajouterA(15, 16, 7);
        g.ajouterA(14, 15, 3);
        g.ajouterA(13, 14, 1);
        g.ajouterA(13, 21, 4);
        g.ajouterA(14, 20, 3);
        g.ajouterA(19, 15, 2);
        g.ajouterA(16, 19, 4);
        g.ajouterA(10, 18, 3);
        g.ajouterA(18, 19, 2);
        g.ajouterA(19, 20, 3);
        g.ajouterA(20, 21, 3);
        g.ajouterA(21, 22, 1);
        g.ajouterA(20, 23, 5);
        g.ajouterA(19, 24, 3);
        g.ajouterA(18, 0, 1);
        g.ajouterA(24, 0, 2);
        g.ajouterA(23, 24, 3);

        //sommet de depart
        int centre =1;

        //scanner
        Scanner sc = new Scanner(System.in);

        //liste des demandes de ramassage
        List<Integer> demandes = new ArrayList<>();

        //liste des tournées (10 ramassage par tournee)
        List<List<Integer>> tournees = new ArrayList<>();

        //date premiere tournee
        int date = 1;

        // Menu (aide chatGPT)
        //index pour les maisons déjà planifié en tournee (ne pas leur planifier plusieurs tournees)
        int index =0;

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Demander un ramassage");
            System.out.println("2. Voir toutes les tournées");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");
            int choix = sc.nextInt();

            if (choix == 1) {
                System.out.print("Entrez le sommet correspondant à votre maison : ");
                int maison = sc.nextInt();

                //ajouter maison du client a liste des demandes
                demandes.add(maison);

                //regarder si on peut faire une nouvelle tournee (10 maisons)
                if(demandes.size() - index == 10){
                    //creer une tournee
                    //prendre les 10 prochaines maisons pas encore ramassées
                    List<Integer> t = new ArrayList<>(demandes.subList(index,index+10));
                    //ajouter la tournee à la liste des tournees
                    tournees.add(t);

                    System.out.println("nouvelle tournee pour date : "+ date);
                    date++;
                    index+=10;
                } else {
                    System.out.println("maison enregistree. Elle sera dans la tournee de la date : "+ date);
                }
            }
            else if (choix == 2) {
                // si aucune tournee planifiées
                if(tournees.isEmpty()){
                    System.out.println("Aucune tournee planifiee ");
                    continue;
                }
                System.out.println("\n - Liste des tournees -");
                //premiere date c'est 1
                int d =1;
                for(List<Integer> t : tournees){
                    System.out.println("tournee de la date "+ d + " : " + t);
                    // creer objet Ramassage1 pour calculer le tsp
                    Ramassage1 ramassage = new Ramassage1(g,t,centre);
                    //chemin optimal
                    List<Integer> chemin = ramassage.tspChemin();
                    //distance totale
                    int distance = ramassage.tspDistance();
                    System.out.println("chemin de la tournee :" + chemin + "\n");
                    System.out.println("distance de la tournee :" + distance + " unites \n");
                    //on incremente la date
                    d++;
                }
            }
            else if (choix == 3) {
                System.out.print("Au revoir");
                break;
            }
            else {
                System.out.println("choix non valide");
            }
        }
        sc.close();
    }
}
