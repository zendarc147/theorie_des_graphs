package theme3.hypothese1;

import java.util.*;

/**
 * Classe contenant les algorithmes de coloration de graphe
 * pour le Thème 3 - Hypothèse 1
 *
 * Algorithme implémenté :
 * - BFS (Breadth-First Search) - Parcours en largeur
 */
public class ColorationGraphe {

    /**
     * ALGORITHME BFS (BREADTH-FIRST SEARCH)
     *
     * Principe :
     * - Parcourir le graphe en largeur (niveau par niveau) à partir d'un sommet de départ
     * - Colorier chaque sommet visité avec la plus petite couleur disponible
     * - Garantit que tous les sommets connectés seront visités
     *
     * Complexité : O(V + E) où V = nombre de secteurs, E = nombre de voisinages
     *
     * @param graphe Le graphe de secteurs à colorier
     * @return Le nombre de couleurs (jours) utilisées
     */
    public static int colorationBFS(GrapheSecteurs graphe) {
        System.out.println("\n=== ALGORITHME : COLORATION BFS (PARCOURS EN LARGEUR) ===");

        // Réinitialiser les couleurs
        for (Secteur s : graphe.getSecteurs()) {
            s.setCouleur(-1);
        }

        int nbCouleursUtilisees = 0;
        Set<Integer> visites = new HashSet<>();
        Queue<Secteur> file = new LinkedList<>();

        // Pour gérer les graphes non connexes, on parcourt tous les secteurs
        for (Secteur secteurDepart : graphe.getSecteurs()) {
            if (visites.contains(secteurDepart.getId())) {
                continue; // Déjà visité dans un parcours précédent
            }

            System.out.println("\n--- Démarrage BFS depuis : " + secteurDepart.getNom() + " ---");
            file.add(secteurDepart);
            visites.add(secteurDepart.getId());

            while (!file.isEmpty()) {
                Secteur secteur = file.poll();
                System.out.println("\nTraitement du secteur : " + secteur.getNom());

                // Trouver la plus petite couleur disponible
                int couleur = trouverPlusPetiteCouleurDisponible(graphe, secteur);

                // Assigner cette couleur au secteur
                secteur.setCouleur(couleur);
                System.out.println("  -> Couleur assignée : " + (couleur + 1) + " (Jour " + (couleur + 1) + ")");

                // Mettre à jour le nombre de couleurs utilisées
                if (couleur + 1 > nbCouleursUtilisees) {
                    nbCouleursUtilisees = couleur + 1;
                }

                // Ajouter les voisins non visités à la file
                for (int idVoisin : graphe.getVoisins(secteur.getId())) {
                    if (!visites.contains(idVoisin)) {
                        visites.add(idVoisin);
                        file.add(graphe.getSecteur(idVoisin));
                        System.out.println("  -> Ajout à la file : " + graphe.getSecteur(idVoisin).getNom());
                    }
                }
            }
        }

        System.out.println("\nNombre total de couleurs utilisées : " + nbCouleursUtilisees);
        return nbCouleursUtilisees;
    }

    /**
     * Trouve la plus petite couleur disponible pour un secteur
     * Une couleur est disponible si aucun voisin ne l'utilise déjà
     *
     * @param graphe Le graphe de secteurs
     * @param secteur Le secteur pour lequel on cherche une couleur
     * @return La plus petite couleur disponible (0, 1, 2, ...)
     */
    private static int trouverPlusPetiteCouleurDisponible(GrapheSecteurs graphe, Secteur secteur) {
        // Liste des couleurs déjà utilisées par les voisins
        List<Integer> couleursVoisins = new ArrayList<>();

        // Récupérer les couleurs de tous les voisins
        for (int idVoisin : graphe.getVoisins(secteur.getId())) {
            Secteur voisin = graphe.getSecteur(idVoisin);
            if (voisin.estColorie()) {
                couleursVoisins.add(voisin.getCouleur());
            }
        }

        // Afficher les couleurs des voisins (pour le débogage)
        if (!couleursVoisins.isEmpty()) {
            System.out.print("  Couleurs des voisins : ");
            for (int i = 0; i < couleursVoisins.size(); i++) {
                System.out.print("Jour " + (couleursVoisins.get(i) + 1));
                if (i < couleursVoisins.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        } else {
            System.out.println("  Aucun voisin colorié");
        }

        // Trouver la plus petite couleur non utilisée
        int couleur = 0;
        while (couleursVoisins.contains(couleur)) {
            couleur++;
        }

        return couleur;
    }

    /**
     * Vérifie si la coloration est valide
     * (aucun secteur voisin n'a la même couleur)
     *
     * @param graphe Le graphe colorié
     * @return true si la coloration est valide, false sinon
     */
    public static boolean verifierColoration(GrapheSecteurs graphe) {
        System.out.println("\n=== VÉRIFICATION DE LA COLORATION ===");

        boolean valide = true;

        for (Secteur secteur : graphe.getSecteurs()) {
            int couleurSecteur = secteur.getCouleur();

            // Vérifier tous les voisins
            for (int idVoisin : graphe.getVoisins(secteur.getId())) {
                Secteur voisin = graphe.getSecteur(idVoisin);
                int couleurVoisin = voisin.getCouleur();

                // Si deux voisins ont la même couleur -> ERREUR
                if (couleurSecteur == couleurVoisin) {
                    System.out.println("ERREUR : " + secteur.getNom() + " et " +
                                     voisin.getNom() + " sont voisins et ont la même couleur !");
                    valide = false;
                }
            }
        }

        if (valide) {
            System.out.println("✓ La coloration est VALIDE : aucun secteur voisin n'a la même couleur");
        } else {
            System.out.println("✗ La coloration est INVALIDE");
        }

        return valide;
    }
}
