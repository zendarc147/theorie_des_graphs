package theme1.prob2.HO2;

import java.util.*;

public class CollectePoubelles_HO2_CasIdeal {

    public static boolean verifierEquilibreDegres(GrapheOriente graphe) {
        System.out.println("\n=== Vérification de l'équilibre des degrés ===");
        boolean tousEquilibres = true;

        for (int i = 0; i < graphe.getNbSommets(); i++) {
            int degIn = graphe.getDegreeEntrant(i);
            int degOut = graphe.getDegreeSortant(i);
            String equilibre = (degIn == degOut) ? "équilibré" : "déséquilibré";
            System.out.println("Sommet " + i + " : deg_in = " + degIn + ", deg_out = " + degOut + " (" + equilibre + ")");

            if (degIn != degOut) {
                tousEquilibres = false;
            }
        }

        return tousEquilibres;
    }

    public static List<Integer> trouverCycleEulerienOriente(GrapheOriente graphe) {
        if (!verifierEquilibreDegres(graphe)) {
            System.out.println("\n❌ Le graphe ne possède pas tous les sommets équilibrés.");
            System.out.println("   Un cycle eulérien orienté n'existe pas.");
            return null;
        }

        System.out.println("\n✓ Tous les sommets sont équilibrés (deg_in = deg_out).");
        System.out.println("  Un cycle eulérien orienté existe !\n");

        // Copie du graphe pour ne pas modifier l'original
        Map<Integer, List<Integer>> grapheCopie = new HashMap<>();
        for (int i = 0; i < graphe.getNbSommets(); i++) {
            grapheCopie.put(i, new ArrayList<>(graphe.getVoisins(i)));
        }

        // Algorithme de Hierholzer pour graphe orienté
        Stack<Integer> pile = new Stack<>();
        List<Integer> cycle = new ArrayList<>();

        int sommetDepart = 0;
        pile.push(sommetDepart);

        System.out.println("=== Algorithme de Hierholzer (graphe orienté) ===");
        System.out.println("Départ du sommet : " + sommetDepart);

        while (!pile.isEmpty()) {
            int sommetCourant = pile.peek();

            if (!grapheCopie.get(sommetCourant).isEmpty()) {
                int voisin = grapheCopie.get(sommetCourant).remove(0);
                pile.push(voisin);
            } else {
                cycle.add(pile.pop());
            }
        }

        Collections.reverse(cycle);
        return cycle;
    }

    public static void afficherParcoursCamion(List<Integer> parcours) {
        if (parcours == null) {
            System.out.println("\nAucun parcours possible.");
            return;
        }

        System.out.println("\n=== Parcours du camion de collecte (HO2) ===");
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

        System.out.println("✓ Le camion a ramassé toutes les poubelles (un côté par passage).");
        System.out.println("✓ Le camion est revenu à son point de départ.");
        System.out.println("Note: Dans HO2, chaque rue à double sens nécessite 2 passages (1 par sens).");
    }
}
