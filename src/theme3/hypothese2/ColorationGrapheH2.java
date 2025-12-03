package theme3.hypothese2;

import java.util.*;

/**
 * Classe contenant l'algorithme de coloration de graphe
 * pour le Thème 3 - Hypothèse 2
 *
 * Contraintes :
 * 1. Deux secteurs voisins ne doivent pas être collectés le même jour
 * 2. Pas plus d'une tournée par jour pour un même secteur (chaque secteur collecté une fois)
 * 3. Chaque secteur a une quantité de déchets qs
 * 4. Un camion a une charge maximale C
 * 5. Nombre de camions disponibles simultanément : N
 * 6. Somme des quantités par jour ≤ N × C (capacité totale de collecte par jour)
 *
 * Algorithme implémenté :
 * - BFS (Breadth-First Search) avec contraintes de capacité
 */
public class ColorationGrapheH2 {

    /**
     * ALGORITHME BFS AVEC CONTRAINTES DE CAPACITÉ
     *
     * @param graphe Le graphe de secteurs à colorier
     * @param capaciteCamion Capacité maximale d'un camion (C)
     * @param nombreCamions Nombre de camions disponibles simultanément (N)
     * @return Le nombre de jours utilisés
     */
    public static int colorationBFS(GrapheSecteurs graphe, int capaciteCamion, int nombreCamions) {
        System.out.println("\n=== ALGORITHME : COLORATION BFS AVEC CONTRAINTES DE CAPACITÉ ===");
        System.out.println("Capacité d'un camion (C) : " + capaciteCamion);
        System.out.println("Nombre de camions (N) : " + nombreCamions);

        int capaciteTotaleParJour = capaciteCamion * nombreCamions;
        System.out.println("Capacité totale par jour (N × C) : " + capaciteTotaleParJour);

        // Réinitialiser les couleurs
        for (Secteur s : graphe.getSecteurs()) {
            s.setCouleur(-1);
        }

        int nbJoursUtilises = 0;
        Set<Integer> visites = new HashSet<>();
        Queue<Secteur> file = new LinkedList<>();

        // Compteur de charge par jour
        Map<Integer, Integer> chargeParJour = new HashMap<>();

        // Pour gérer les graphes non connexes, on parcourt tous les secteurs
        for (Secteur secteurDepart : graphe.getSecteurs()) {
            if (visites.contains(secteurDepart.getId())) {
                continue;
            }

            System.out.println("\n--- Démarrage BFS depuis : " + secteurDepart.getNom() + " ---");
            file.add(secteurDepart);
            visites.add(secteurDepart.getId());

            while (!file.isEmpty()) {
                Secteur secteur = file.poll();
                System.out.println("\nTraitement du secteur : " + secteur.getNom()
                    + " (Déchets: " + secteur.getQuantiteDechets() + ")");

                // Trouver la plus petite couleur disponible en respectant les contraintes
                int couleur = trouverPlusPetiteCouleurDisponible(
                    graphe, secteur, chargeParJour, capaciteTotaleParJour);

                // Assigner cette couleur au secteur
                secteur.setCouleur(couleur);
                System.out.println("  -> Jour assigné : " + (couleur + 1));

                // Mettre à jour la charge pour ce jour
                int nouvelleCharge = chargeParJour.getOrDefault(couleur, 0) + secteur.getQuantiteDechets();
                chargeParJour.put(couleur, nouvelleCharge);
                System.out.println("  -> Charge du Jour " + (couleur + 1) + " : "
                    + nouvelleCharge + "/" + capaciteTotaleParJour);

                // Mettre à jour le nombre de jours utilisés
                if (couleur + 1 > nbJoursUtilises) {
                    nbJoursUtilises = couleur + 1;
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

        System.out.println("\n=== RÉSUMÉ ===");
        System.out.println("Nombre total de jours utilisés : " + nbJoursUtilises);
        for (int jour = 0; jour < nbJoursUtilises; jour++) {
            int charge = chargeParJour.getOrDefault(jour, 0);
            System.out.println("  Jour " + (jour + 1) + " : " + charge + "/" + capaciteTotaleParJour);
        }

        return nbJoursUtilises;
    }

    /**
     * Trouve la plus petite couleur (jour) disponible pour un secteur
     * en respectant les contraintes de voisinage ET de capacité
     */
    private static int trouverPlusPetiteCouleurDisponible(
            GrapheSecteurs graphe, Secteur secteur,
            Map<Integer, Integer> chargeParJour, int capaciteTotaleParJour) {

        // Liste des couleurs déjà utilisées par les voisins
        Set<Integer> couleursVoisins = new HashSet<>();

        // Récupérer les couleurs de tous les voisins
        for (int idVoisin : graphe.getVoisins(secteur.getId())) {
            Secteur voisin = graphe.getSecteur(idVoisin);
            if (voisin.estColorie()) {
                couleursVoisins.add(voisin.getCouleur());
            }
        }

        // Afficher les couleurs des voisins
        if (!couleursVoisins.isEmpty()) {
            System.out.print("  Jours des voisins : ");
            List<Integer> sorted = new ArrayList<>(couleursVoisins);
            Collections.sort(sorted);
            for (int i = 0; i < sorted.size(); i++) {
                System.out.print("Jour " + (sorted.get(i) + 1));
                if (i < sorted.size() - 1) System.out.print(", ");
            }
            System.out.println();
        } else {
            System.out.println("  Aucun voisin colorié");
        }

        // Trouver la plus petite couleur disponible
        int couleur = 0;
        while (true) {
            // Vérifier si cette couleur est utilisée par un voisin
            if (couleursVoisins.contains(couleur)) {
                couleur++;
                continue;
            }

            // Vérifier si ce jour a assez de capacité pour ce secteur
            int chargeActuelle = chargeParJour.getOrDefault(couleur, 0);
            int chargeApres = chargeActuelle + secteur.getQuantiteDechets();

            if (chargeApres > capaciteTotaleParJour) {
                System.out.println("  Le Jour " + (couleur + 1) + " n'a pas assez de capacité ("
                    + chargeActuelle + " + " + secteur.getQuantiteDechets()
                    + " > " + capaciteTotaleParJour + ")");
                couleur++;
                continue;
            }

            // Cette couleur est disponible
            break;
        }

        return couleur;
    }

    /**
     * Vérifie si la coloration est valide
     */
    public static boolean verifierColoration(GrapheSecteurs graphe, int capaciteCamion, int nombreCamions) {
        System.out.println("\n=== VÉRIFICATION DE LA COLORATION ===");

        boolean valide = true;
        int capaciteTotaleParJour = capaciteCamion * nombreCamions;

        // Vérifier la contrainte de voisinage
        for (Secteur secteur : graphe.getSecteurs()) {
            int couleurSecteur = secteur.getCouleur();

            for (int idVoisin : graphe.getVoisins(secteur.getId())) {
                Secteur voisin = graphe.getSecteur(idVoisin);
                int couleurVoisin = voisin.getCouleur();

                if (couleurSecteur == couleurVoisin) {
                    System.out.println("ERREUR : " + secteur.getNom() + " et " +
                                     voisin.getNom() + " sont voisins et ont le même jour !");
                    valide = false;
                }
            }
        }

        // Vérifier la contrainte de capacité par jour
        Map<Integer, Integer> chargeParJour = new HashMap<>();
        for (Secteur s : graphe.getSecteurs()) {
            int jour = s.getCouleur();
            int charge = chargeParJour.getOrDefault(jour, 0) + s.getQuantiteDechets();
            chargeParJour.put(jour, charge);
        }

        for (Map.Entry<Integer, Integer> entry : chargeParJour.entrySet()) {
            int jour = entry.getKey();
            int charge = entry.getValue();
            if (charge > capaciteTotaleParJour) {
                System.out.println("ERREUR : Le Jour " + (jour + 1) + " a une charge de " + charge
                    + " (max = " + capaciteTotaleParJour + ")");
                valide = false;
            }
        }

        if (valide) {
            System.out.println("✓ La coloration est VALIDE :");
            System.out.println("  - Aucun secteur voisin n'est collecté le même jour");
            System.out.println("  - Chaque jour respecte la capacité de " + capaciteTotaleParJour + " unités (N × C)");
        } else {
            System.out.println("✗ La coloration est INVALIDE");
        }

        return valide;
    }
}
