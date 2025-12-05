package theme1.prob2.HO3;

import java.util.*;

public class CollectePoubelles_HO3_CasIdeal {

    public static boolean verifierEquilibreDegres(GrapheMixte graphe) {
        System.out.println("\n=== Vérification de l'équilibre des degrés (graphe mixte) ===");
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

    public static List<Integer> trouverCycleEulerienMixte(GrapheMixte graphe) {
        if (!verifierEquilibreDegres(graphe)) {
            System.out.println("\n❌ Le graphe ne possède pas tous les sommets équilibrés.");
            System.out.println("   Un cycle eulérien mixte n'existe pas.");
            return null;
        }

        System.out.println("\n✓ Tous les sommets sont équilibrés (deg_in = deg_out).");
        System.out.println("  Un cycle eulérien mixte existe !\n");

        // Copie du graphe pour ne pas modifier l'original
        Map<Integer, List<Integer>> grapheCopie = new HashMap<>();
        for (int i = 0; i < graphe.getNbSommets(); i++) {
            grapheCopie.put(i, new ArrayList<>(graphe.getVoisins(i)));
        }

        // Algorithme de Hierholzer adapté pour graphe mixte
        Stack<Integer> pile = new Stack<>();
        List<Integer> cycle = new ArrayList<>();

        int sommetDepart = 0;
        pile.push(sommetDepart);

        System.out.println("=== Algorithme de Hierholzer (graphe mixte) ===");
        System.out.println("Départ du sommet : " + sommetDepart);

        while (!pile.isEmpty()) {
            int sommetCourant = pile.peek();

            if (!grapheCopie.get(sommetCourant).isEmpty()) {
                int voisin = grapheCopie.get(sommetCourant).remove(0);

                // Si c'est une arête non orientée, on doit aussi retirer l'arc inverse
                if (graphe.estAreteNonOrientee(sommetCourant, voisin)) {
                    grapheCopie.get(voisin).remove(Integer.valueOf(sommetCourant));
                }
                // Si c'est une arête double, on doit aussi retirer l'arc inverse
                else if (graphe.estAreteDouble(sommetCourant, voisin)) {
                    grapheCopie.get(voisin).remove(Integer.valueOf(sommetCourant));
                }
                // Sinon c'est une arête orientée, pas besoin de retirer l'inverse

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

        System.out.println("\n=== Parcours du camion de collecte (HO3 - Graphe mixte) ===");
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

        System.out.println("✓ Le camion a ramassé toutes les poubelles.");
        System.out.println("✓ Le camion est revenu à son point de départ.");
        System.out.println("\nNote HO3 (graphe mixte) :");
        System.out.println("  - RUE (1 voie) : ramassage des 2 côtés en 1 passage");
        System.out.println("  - RUE_DOUBLE (multi-voies) : 2 passages (1 par sens)");
        System.out.println("  - RUE_ORIENTEE (sens unique) : 1 passage");
    }
}
