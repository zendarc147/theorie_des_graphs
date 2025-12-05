import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quitter = false;

        // --------- Choix du type d'utilisateur ---------
        int typeUtilisateur = choisirProfil(scanner);
        if (typeUtilisateur == 0) {
            System.out.println("Au revoir !");
            scanner.close();
            return;
        }

        String libelleProfil = (typeUtilisateur == 1)
                ? "Utilisateur (habitant / citoyen)"
                : "Collectivité (en charge de la collecte et du transport des déchets)";

        System.out.println("\nVous êtes connecté en tant que : " + libelleProfil + "\n");
        // ------------------------------------------------

        while (!quitter) {

            // --------- Afficher le bon menu selon le profil ---------
            if (typeUtilisateur == 1) {
                afficherMenuUtilisateur();
            } else {
                afficherMenuCollectivite();
            }
            System.out.print("Votre choix : ");

            int choix = 0;
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.\n");
            }

            switch (choix) {
                case 1:
                    menuTheme1(scanner, typeUtilisateur);
                    break;
                case 2:
                    menuTheme2(scanner, typeUtilisateur);
                    break;
                case 3:
                    menuTheme3(scanner, typeUtilisateur);
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

    // --------Menu de choix du profil --------
    private static int choisirProfil(Scanner scanner) {
        while (true) {
            System.out.println("==================================================");
            System.out.println("          Sélection du type d'utilisateur");
            System.out.println("==================================================");
            System.out.println("1 - Utilisateur (habitant / citoyen)");
            System.out.println("2 - Collectivité en charge de la collecte");
            System.out.println("0 - Quitter");
            System.out.println("--------------------------------------------------");
            System.out.print("Votre choix : ");

            int choix;
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.\n");
                continue;
            }

            if (choix == 0 || choix == 1 || choix == 2) {
                return choix;
            } else {
                System.out.println("Choix invalide, réessayez.\n");
            }
        }
    }
    // ------------------------------------------------------------

    // -------- MENUS PRINCIPAUX DIFFÉRENTS --------

    // Menu pour un utilisateur lambda
    private static void afficherMenuUtilisateur() {
        System.out.println("==================================================");
        System.out.println("      PROJET - GESTION DES DECHETS (Utilisateur)");
        System.out.println("==================================================");
        System.out.println("1 - Ramassage aux pieds des habitations");
        System.out.println("2 - Points de collecte");
        System.out.println("3 - Jours de passage dans les quartiers");
        System.out.println("0 - Quitter");
        System.out.println("--------------------------------------------------");
    }

    // Menu pour la collectivité
    private static void afficherMenuCollectivite() {
        System.out.println("==================================================");
        System.out.println("   PROJET - GESTION DES DECHETS (Collectivité)");
        System.out.println("==================================================");
        System.out.println("1 - Optimiser le ramassage aux pieds des habitations");
        System.out.println("2 - Optimiser les tournées des points de collecte");
        System.out.println("3 - Planifier les jours de passage dans les quartiers");
        System.out.println("0 - Quitter");
        System.out.println("--------------------------------------------------");
    }

    // -------- MENUS THEMATIQUES AVEC 2 VERSIONS --------

    private static void menuTheme1(Scanner scanner, int typeUtilisateur) {
        boolean retour = false;
        while (!retour) {

            if (typeUtilisateur == 1) {
                // Version UTILISATEUR
                System.out.println("\n--- Thème 1 (Utilisateur) : Ramassage aux pieds des habitations ---");
                System.out.println("1 - Consulter les informations sur la collecte des encombrants");
                System.out.println("2 - Consulter les informations sur la collecte des poubelles");
            } else {
                // Version COLLECTIVITÉ
                System.out.println("\n--- Thème 1 (Collectivité) : Ramassage aux pieds des habitations ---");
                System.out.println("1 - Problématique 1 : Optimiser la collecte des encombrants");
                System.out.println("2 - Problématique 2 : Optimiser la collecte des poubelles aux pieds des habitations");
            }

            System.out.println("0 - Retour au menu principal");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Affichage des informations sur la collecte des encombrants (vue Utilisateur)...");
                    } else {
                        System.out.println(">> Lancement de l'optimisation de la collecte des encombrants (Thème 1 / Prob 1)...");
                    }
                    break;
                case 2:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Affichage des informations sur la collecte des poubelles (vue Utilisateur)...");
                    } else {
                        System.out.println(">> Lancement de l'optimisation de la collecte des poubelles (Thème 1 / Prob 2)...");
                    }
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        }
    }

    private static void menuTheme2(Scanner scanner, int typeUtilisateur) {
        boolean retour = false;
        while (!retour) {

            if (typeUtilisateur == 1) {
                // Version UTILISATEUR
                System.out.println("\n--- Thème 2 (Utilisateur) : Points de collecte ---");
                System.out.println("1 - Visualiser l'ordre de passage des points de collecte");
                System.out.println("2 - Comparer différents trajets possibles");
            } else {
                // Version COLLECTIVITÉ
                System.out.println("\n--- Thème 2 (Collectivité) : Ramassages des points de collecte ---");
                System.out.println("1 - Approche 1 : Plus proche voisin");
                System.out.println("2 - Approche 2 : MST (arbre couvrant de poids minimum)");
            }

            System.out.println("0 - Retour au menu principal");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Visualisation de la tournée des points de collecte (vue Utilisateur)...");
                    } else {
                        System.out.println(">> Lancement de l'approche Plus proche voisin...");
                    }
                    break;
                case 2:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Comparaison de plusieurs trajets de collecte (vue Utilisateur)...");
                    } else {
                        System.out.println(">> Lancement de l'approche MST...");
                    }
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("Choix invalide, réessayez.");
            }
        }
    }

    private static void menuTheme3(Scanner scanner, int typeUtilisateur) {
        boolean retour = false;
        while (!retour) {

            if (typeUtilisateur == 1) {
                // Version UTILISATEUR
                System.out.println("\n--- Thème 3 (Utilisateur) : Jours de passage ---");
                System.out.println("1 - Consulter les jours de passage dans mon quartier");
                System.out.println("2 - Simuler le changement de jour de passage");
            } else {
                // Version COLLECTIVITÉ
                System.out.println("\n--- Thème 3 (Collectivité) : Planification des jours de passage ---");
                System.out.println("1 - Contraintes géographiques (voisinage des secteurs)");
                System.out.println("2 - Contraintes de capacité (déchets, camions, charges)");
            }

            System.out.println("0 - Retour au menu principal");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Affichage des jours de passage pour le quartier (vue Utilisateur)...");
                    } else {
                        System.out.println(">> Planification avec contraintes géographiques...");
                    }
                    break;
                case 2:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Simulation de changement de jour de passage (vue Utilisateur)...");
                    } else {
                        System.out.println(">> Planification avec contraintes de capacité...");
                    }
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
