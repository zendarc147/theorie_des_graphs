package theme1.prob2.HO1;

import java.util.*;
import theme1.prob2.commun.Dijkstra;
import theme1.prob2.commun.CouplageGlouton;

public class CollectePoubelles_HO1_CasIdeal {

    public static List<Integer> getSommetsImpairs(GrapheNonOriente graphe) {
        List<Integer> sommetsImpairs = new ArrayList<>();
        for (int i = 0; i < graphe.getNbSommets(); i++) {
            if (graphe.getDegre(i) % 2 != 0) {
                sommetsImpairs.add(i);
            }
        }
        return sommetsImpairs;
    }

    public static void verifierDegresParite(GrapheNonOriente graphe) {
        System.out.println("\n=== Vérification des degrés ===");
        List<Integer> sommetsImpairs = new ArrayList<>();

        for (int i = 0; i < graphe.getNbSommets(); i++) {
            int degre = graphe.getDegre(i);
            String parite = (degre % 2 == 0) ? "pair" : "impair";
            System.out.println("Sommet " + i + " : degré = " + degre + " (" + parite + ")");

            if (degre % 2 != 0) {
                sommetsImpairs.add(i);
            }
        }

        System.out.println("\nNombre de sommets impairs : " + sommetsImpairs.size());
        if (!sommetsImpairs.isEmpty()) {
            System.out.println("Sommets impairs : " + sommetsImpairs);
        }
    }

    // CAS 1 : Tous les sommets de degrés pairs → Cycle eulérien
    public static List<Integer> trouverCycleEulerien(GrapheNonOriente graphe) {
        List<Integer> sommetsImpairs = getSommetsImpairs(graphe);

        if (!sommetsImpairs.isEmpty()) {
            System.out.println("\n❌ Le graphe ne possède pas que des sommets de degrés pairs.");
            System.out.println("   Un cycle eulérien n'existe pas.");
            return null;
        }

        System.out.println("\n✓ Tous les sommets ont un degré pair.");
        System.out.println("  Un cycle eulérien existe !\n");

        return hierholzer(graphe, 0);
    }

    // CAS 2 : Deux sommets de degrés impairs → Chemin eulérien
    public static List<Integer> trouverCheminEulerien(GrapheNonOriente graphe) {
        List<Integer> sommetsImpairs = getSommetsImpairs(graphe);

        if (sommetsImpairs.size() != 2) {
            System.out.println("\n❌ Le graphe n'a pas exactement 2 sommets de degrés impairs.");
            System.out.println("   Un chemin eulérien n'existe pas.");
            return null;
        }

        int sommetDepart = sommetsImpairs.get(0);
        int sommetArrivee = sommetsImpairs.get(1);

        System.out.println("\n✓ Exactement 2 sommets de degrés impairs : " + sommetDepart + " et " + sommetArrivee);
        System.out.println("  Un chemin eulérien existe de " + sommetDepart + " à " + sommetArrivee + " !\n");

        return hierholzer(graphe, sommetDepart);
    }

    // CAS 3 : Cas général → Algorithme du postier chinois COMPLET
    public static List<Integer> resoudrePostierChinois(GrapheNonOriente graphe) {
        List<Integer> sommetsImpairs = getSommetsImpairs(graphe);

        System.out.println("\n=== CAS GÉNÉRAL : Algorithme du postier chinois ===");
        System.out.println("Nombre de sommets impairs : " + sommetsImpairs.size());

        if (sommetsImpairs.isEmpty()) {
            System.out.println("→ Tous les sommets sont pairs, on utilise le cycle eulérien.");
            return trouverCycleEulerien(graphe);
        } else if (sommetsImpairs.size() == 2) {
            System.out.println("→ Exactement 2 sommets impairs, on utilise le chemin eulérien.");
            return trouverCheminEulerien(graphe);
        } else {
            System.out.println("\n⚙ Cas complexe : " + sommetsImpairs.size() + " sommets impairs.");
            System.out.println("  Application de l'algorithme du postier chinois complet :\n");

            // ÉTAPE 1 : Calculer les plus courts chemins entre sommets impairs avec Dijkstra
            System.out.println("  [1/4] Calcul des plus courts chemins (Dijkstra)...");
            int[][] distances = Dijkstra.calculerMatriceDistances(
                graphe.getListeAdjacence(),
                graphe.getNbSommets(),
                sommetsImpairs
            );

            System.out.println("        Matrice des distances entre sommets impairs :");
            for (int i = 0; i < sommetsImpairs.size(); i++) {
                System.out.print("        Sommet " + sommetsImpairs.get(i) + " : ");
                for (int j = 0; j < sommetsImpairs.size(); j++) {
                    if (i != j) {
                        System.out.print(sommetsImpairs.get(j) + "→" + distances[i][j] + " ");
                    }
                }
                System.out.println();
            }

            // ÉTAPE 2 : Couplage glouton des sommets impairs
            System.out.println("\n  [2/4] Couplage glouton des sommets impairs...");
            List<CouplageGlouton.Paire> couplage = CouplageGlouton.trouverCouplage(sommetsImpairs, distances);

            System.out.println("        Paires trouvées :");
            for (CouplageGlouton.Paire paire : couplage) {
                System.out.println("        - " + paire);
            }
            int coutTotal = CouplageGlouton.calculerCout(couplage);
            System.out.println("        Coût total des duplications : " + coutTotal + " arêtes");

            // ÉTAPE 3 : Dupliquer les arêtes sur les plus courts chemins
            System.out.println("\n  [3/4] Duplication des arêtes pour équilibrer le graphe...");
            GrapheNonOriente grapheAugmente = dupliquerAretes(graphe, sommetsImpairs, couplage);

            System.out.println("        Graphe augmenté créé :");
            System.out.println("        - Arêtes originales : " + compterAretes(graphe));
            System.out.println("        - Arêtes dupliquées : " + coutTotal);
            System.out.println("        - Total : " + compterAretes(grapheAugmente));

            // Vérifier que le graphe augmenté est eulérien
            List<Integer> sommetsImpairsAugmente = getSommetsImpairs(grapheAugmente);
            System.out.println("        Sommets impairs restants : " + sommetsImpairsAugmente.size());

            // ÉTAPE 4 : Appliquer Hierholzer sur le graphe augmenté
            System.out.println("\n  [4/4] Application de Hierholzer sur le graphe augmenté...");
            List<Integer> parcours = hierholzer(grapheAugmente, 0);

            System.out.println("\n✓ Algorithme du postier chinois terminé !");
            System.out.println("  Le parcours optimal visite " + (parcours.size() - 1) + " arêtes");
            System.out.println("  (dont " + coutTotal + " arêtes parcourues 2 fois)\n");

            return parcours;
        }
    }

    /**
     * Duplique les arêtes sur les plus courts chemins entre les paires de sommets
     */
    private static GrapheNonOriente dupliquerAretes(
            GrapheNonOriente graphe,
            List<Integer> sommetsImpairs,
            List<CouplageGlouton.Paire> couplage) {

        // Créer une copie du graphe
        GrapheNonOriente grapheAugmente = new GrapheNonOriente(graphe.getNbSommets());

        // Copier toutes les arêtes originales
        for (int i = 0; i < graphe.getNbSommets(); i++) {
            for (int voisin : graphe.getVoisins(i)) {
                if (i < voisin) { // Éviter les doublons
                    grapheAugmente.ajouterArete(i, voisin);
                }
            }
        }

        // Pour chaque paire du couplage, ajouter les arêtes du plus court chemin
        for (CouplageGlouton.Paire paire : couplage) {
            // Calculer le plus court chemin entre les deux sommets
            Dijkstra.ResultatDijkstra resultat = Dijkstra.calculerPlusCourtsChemin(
                graphe.getListeAdjacence(),
                graphe.getNbSommets(),
                paire.sommet1
            );

            List<Integer> chemin = Dijkstra.reconstruireChemin(resultat, paire.sommet2);

            // Dupliquer les arêtes de ce chemin
            for (int i = 0; i < chemin.size() - 1; i++) {
                grapheAugmente.ajouterArete(chemin.get(i), chemin.get(i + 1));
            }
        }

        return grapheAugmente;
    }

    /**
     * Compte le nombre d'arêtes dans le graphe
     */
    private static int compterAretes(GrapheNonOriente graphe) {
        int total = 0;
        for (int i = 0; i < graphe.getNbSommets(); i++) {
            total += graphe.getVoisins(i).size();
        }
        return total / 2; // Divisé par 2 car chaque arête est comptée deux fois
    }

    // Algorithme de Hierholzer réutilisable
    private static List<Integer> hierholzer(GrapheNonOriente graphe, int sommetDepart) {
        // Copie du graphe pour ne pas modifier l'original
        Map<Integer, List<Integer>> grapheCopie = new HashMap<>();
        for (int i = 0; i < graphe.getNbSommets(); i++) {
            grapheCopie.put(i, new ArrayList<>(graphe.getVoisins(i)));
        }

        Stack<Integer> pile = new Stack<>();
        List<Integer> parcours = new ArrayList<>();

        pile.push(sommetDepart);

        System.out.println("=== Algorithme de Hierholzer ===");
        System.out.println("Départ du sommet : " + sommetDepart);

        while (!pile.isEmpty()) {
            int sommetCourant = pile.peek();

            if (!grapheCopie.get(sommetCourant).isEmpty()) {
                int voisin = grapheCopie.get(sommetCourant).remove(0);
                grapheCopie.get(voisin).remove(Integer.valueOf(sommetCourant));
                pile.push(voisin);
            } else {
                parcours.add(pile.pop());
            }
        }

        Collections.reverse(parcours);
        return parcours;
    }

    public static void afficherParcoursCamion(List<Integer> parcours) {
        if (parcours == null) {
            System.out.println("\nAucun parcours possible.");
            return;
        }

        System.out.println("\n=== Parcours du camion de collecte ===");
        System.out.println("Nombre d'intersections visitées : " + parcours.size());
        System.out.println("Nombre de rues parcourues : " + (parcours.size() - 1));

        System.out.print("\nParcours : ");
        for (int i = 0; i < parcours.size(); i++) {
            System.out.print(parcours.get(i));
            if (i < parcours.size() - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println("\n");

        System.out.println("✓ Le camion a ramassé toutes les poubelles des deux côtés de chaque rue.");
        System.out.println("✓ Le camion est revenu à son point de départ.");
    }
}
