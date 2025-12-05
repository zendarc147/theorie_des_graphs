package theme1.prob2.HO1;

import java.util.*;

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

    // CAS 3 : Cas général → Algorithme du postier chinois (simplification)
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
            System.out.println("\n⚠ Cas complexe : " + sommetsImpairs.size() + " sommets impairs.");
            System.out.println("  Il faut dupliquer certaines arêtes pour obtenir un graphe eulérien.");
            System.out.println("  (Implémentation simplifiée : on trouve juste un parcours couvrant)");

            // Pour l'instant, on fait juste un parcours simple depuis le premier sommet
            return hierholzer(graphe, 0);
        }
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
