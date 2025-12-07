import theme2.approche1.Mt2;
import theme2.approche1.Mt2Citoyen;
import theme2.approche2.Mt22;
import theme2.approche2.Mt22AvecCapacite;
import theme2.approche2.Mt22Citoyen;
import theme1.prob1.Hyp1.H01.*;
import theme1.prob1.Hyp1.H02.*;
import theme1.prob1.Hyp1.H03.*;
import theme1.prob1.Hyp2.*;

import java.util.Scanner;

public class Main {
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

    // -------- MENUS THEMATIQUES --------

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
                        System.out.println("1.Sans tournee (hyp1)\n");
                        System.out.println("2.Avec tournees (hyp2)\n");
                        int c = Integer.parseInt(scanner.nextLine());
                        switch (c){
                            case 1:
                                System.out.println("1.H01\n");
                                System.out.println("2.H02\n");
                                System.out.println("3.H03\n");
                                int c2 = Integer.parseInt(scanner.nextLine());
                                switch (c2) {
                                    case 1:
                                        //prob1 hyp1 H01
                                        theme1.prob1.Hyp1.H01.Main11.main(new String[]{});
                                        break;
                                    case 2:
                                        //prob1 hyp1 H02
                                        src.theme1.prob1.Hyp1.H02.Main12.main(new String[]{});
                                        break;
                                    case 3:
                                        //prob 1 hyp1 H03
                                        src.theme1.prob1.Hyp1.H03.Main13.main(new String[]{});
                                        break;
                                }
                            case 2:
                                System.out.println("1.H01\n");
                                System.out.println("2.H02\n");
                                int c3 = Integer.parseInt(scanner.nextLine());
                                switch (c3){
                                    case 1:
                                        //prob1 hyp2 H01
                                        theme1.prob1.Hyp2.H01.Main21.main(new String[]{});
                                        break;
                                    case 2:
                                        //prob1 hyp2 H02
                                        theme1.prob1.Hyp2.H02.Main22.main(new String[]{});
                                        break;
                                }
                        }
                    } else {
                        System.out.println(">> Lancement de l'optimisation de la collecte des encombrants (Thème 1 / Prob 1)...");
                    }
                    break;
                case 2:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Affichage des informations sur la collecte des poubelles (vue Utilisateur)...");
                        menuProblematique2(scanner);
                    } else {
                        menuProblematique2(scanner);
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

    private static void menuProblematique2(Scanner scanner) {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Problématique 2 : Collecte des poubelles (Hypothèses) ---");
            System.out.println("1 - HO1 : Graphe non orienté (rues à double sens)");
            System.out.println("2 - HO2 : Graphe orienté (rues à sens unique)");
            System.out.println("3 - HO3 : Graphe mixte (combinaison)");
            System.out.println("0 - Retour");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    System.out.println(">> Lancement de HO1...\n");
                    theme1.prob2.HO1.Main.executer(scanner);
                    break;
                case 2:
                    System.out.println(">> Lancement de HO2...\n");
                    theme1.prob2.HO2.Main.executer(scanner);
                    break;
                case 3:
                    System.out.println(">> Lancement de HO3...\n");
                    theme1.prob2.HO3.Main.executer(scanner);
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
                        System.out.println(">> Visualisation de la tournée des points de collecte (vue Utilisateur)...\n");
                        Mt2Citoyen.main(new String[0]);
                    } else {
                        System.out.println(">> Lancement de l'approche Plus proche voisin...\n");
                        Mt2.main(new String[0]);
                    }
                    break;
                case 2:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Comparaison de plusieurs trajets de collecte (vue Utilisateur)...\n");
                        Mt22Citoyen.main(new String[0]);
                    } else {
                        System.out.println(">> Lancement de l'approche MST...\n");
                        Mt22AvecCapacite.main(new String[0]);
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
                System.out.println("1 - Hypothèse 1 : Contraintes géographiques (voisinage des secteurs)");
                System.out.println("2 - Hypothèse 2 : Contraintes de capacité (déchets, camions, charges)");
            }

            System.out.println("0 - Retour au menu principal");
            System.out.print("Votre choix : ");

            int choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Affichage des jours de passage pour le quartier (vue Utilisateur)...\n");
                        theme3.hypothese1.Main.executer(scanner);
                    } else {
                        System.out.println(">> Lancement de l'Hypothèse 1 (Contraintes géographiques)...\n");
                        theme3.hypothese1.Main.executer(scanner);
                    }
                    break;
                case 2:
                    if (typeUtilisateur == 1) {
                        System.out.println(">> Simulation de changement de jour de passage (vue Utilisateur)...\n");
                        theme3.hypothese2.Main.executer(scanner);
                    } else {
                        System.out.println(">> Lancement de l'Hypothèse 2 (Contraintes de capacité)...\n");
                        theme3.hypothese2.Main.executer(scanner);
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
