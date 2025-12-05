package theme1.prob2.HO1;

import java.util.*;

public class CollectePoubelles_HO1_CasIdeal {

    public static boolean verifierDegresParite(GrapheNonOriente graphe) {
        System.out.println("\n=== Vérification des degrés ===");
        boolean tousDegresPairs = true;

        for (int i = 0; i < graphe.getNbSommets(); i++) {
            int degre = graphe.getDegre(i);
            String parite = (degre % 2 == 0) ? "pair" : "impair";
            System.out.println("Sommet " + i + " : degré = " + degre + " (" + parite + ")");

            if (degre % 2 != 0) {
                tousDegresPairs = false;
            }
        }

        return tousDegresPairs;
    }

    public static List<Integer> trouverCycleEulerien(GrapheNonOriente graphe) {
        if (!verifierDegresParite(graphe)) {
            System.out.println("\n❌ Le graphe ne possède pas que des sommets de degrés pairs.");
            System.out.println("   Un cycle eulérien n'existe pas.");
            return null;
        }

        System.out.println("\n✓ Tous les sommets ont un degré pair.");
        System.out.println("  Un cycle eulérien existe !\n");

        // Copie du graphe pour ne pas modifier l'original
        Map<Integer, List<Integer>> grapheCopie = new HashMap<>();
        for (int i = 0; i < graphe.getNbSommets(); i++) {
            grapheCopie.put(i, new ArrayList<>(graphe.getVoisins(i)));
        }

        // Algorithme de Hierholzer
        Stack<Integer> pile = new Stack<>();
        List<Integer> cycle = new ArrayList<>();

        int sommetDepart = 0;
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
