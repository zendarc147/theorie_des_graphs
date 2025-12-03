import java.util.Scanner;
public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;

        while (!quitter) {
            afficherMenuPrincipal();
            System.out.print("Votre choix : ");

            int choix = 0;
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.\n");
                //continue;
            }

            switch (choix) {
                case 1:
                    menuTheme1(scanner);
                    break;
                case 2:
                    menuTheme2(scanner);
                    break;
                case 3:
                    menuTheme3(scanner);
                    break;
                case 0:
                    quitter = true;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, réessayez.\n");
            }
        }
        scanner.close();
    }

// -------- MENUS --------

    private static void afficherMenuPrincipal() {
        System.out.println("==================================================");
        System.out.println("      PROJET - GESTION DES DECHETS (Théorie des Graphes)");
        System.out.println("==================================================");
        System.out.println("1 - Thème 1 : Optimiser le ramassage aux pieds des habitations");
        System.out.println("2 - Thème 2 : Optimiser les ramassages des points de collecte");
        System.out.println("3 - Thème 3 : Planifier les jours de passage dans les quartiers");
        System.out.println("0 - Quitter");
        System.out.println("--------------------------------------------------");
    }

    private static void menuTheme1(Scanner scanner) {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Thème 1 : Ramassage aux pieds des habitations ---");
            System.out.println("1 - Problématique 1 : Collecte des encombrants");
            System.out.println("2 - Problématique 2 : Collecte des poubelles aux pieds des habitations");
            System.out.println("0 - Retour au menu principal");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    // à faire : appeler la méthode qui gère la prob. 1
                    System.out.println(">> Lancement de la collecte des encombrants (Thème 1 / Prob 1)...");
                    break;
                case 2:
                    // à faire : appeler la méthode qui gère la prob. 2
                    System.out.println(">> Lancement de la collecte des poubelles (Thème 1 / Prob 2)...");
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        }
    }

    private static void menuTheme2(Scanner scanner) {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Thème 2 : Ramassages des points de collecte ---");
            System.out.println("1 - Approche 1 : Plus proche voisin");
            System.out.println("2 - Approche 2 : MST (arbre couvrant de poids minimum)");
            System.out.println("0 - Retour au menu principal");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    // à faire : lancer l'algo "plus proche voisin"
                    System.out.println(">> Lancement de l'approche Plus proche voisin...");
                    break;
                case 2:
                    // à faire : lancer l'approche MST
                    System.out.println(">> Lancement de l'approche MST...");
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        }
    }

    private static void menuTheme3(Scanner scanner) {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Thème 3 : Planification des jours de passage ---");
            System.out.println("1 - Hypothèse 1 : Contraintes géographiques (voisinage des secteurs)");
            System.out.println("2 - Hypothèse 2 : Contraintes de capacité (déchets, camions, charges)");
            System.out.println("0 - Retour au menu principal");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    // à faire : gestion de la planification avec contraintes géographiques
                    System.out.println(">> Planification avec contraintes géographiques...");
                    break;
                case 2:
                    // à faire : gestion de la planification avec contraintes de capacité
                    System.out.println(">> Planification avec contraintes de capacité...");
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        }
    }
}

