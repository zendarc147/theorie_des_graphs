package theme2.approche2;

import theme2.approche1.GrapheNonOriente;
import theme2.approche1.ItineraireVoyageur;
import java.util.*;

public class AlgorithmeMST {

    public static List<Arete> kruskal(GrapheNonOriente graphe) {
        List<Arete> aretes = new ArrayList<>();

        for (String sommet1 : graphe.getSommet()) {
            Map<String, Integer> voisins = graphe.getVoisins(sommet1);
            for (Map.Entry<String, Integer> voisin : voisins.entrySet()) {
                String sommet2 = voisin.getKey();
                int poids = voisin.getValue();

                if (sommet1.compareTo(sommet2) < 0) {
                    aretes.add(new Arete(sommet1, sommet2, poids));
                }
            }
        }

        Collections.sort(aretes);

        UnionFind uf = new UnionFind(graphe.getSommet());
        List<Arete> mst = new ArrayList<>();

        for (Arete arete : aretes) {
            if (uf.union(arete.getSommet1(), arete.getSommet2())) {
                mst.add(arete);
            }
        }

        return mst;
    }

    public static Map<String, List<String>> construireArbreMST(List<Arete> mst) {
        Map<String, List<String>> arbre = new HashMap<>();

        for (Arete arete : mst) {
            arbre.putIfAbsent(arete.getSommet1(), new ArrayList<>());
            arbre.putIfAbsent(arete.getSommet2(), new ArrayList<>());
            arbre.get(arete.getSommet1()).add(arete.getSommet2());
            arbre.get(arete.getSommet2()).add(arete.getSommet1());
        }

        return arbre;
    }

    public static List<String> parcoursPrefixe(Map<String, List<String>> arbre, String racine) {
        List<String> parcours = new ArrayList<>();
        Set<String> visites = new HashSet<>();
        parcoursPrefixeRecursif(arbre, racine, visites, parcours);
        return parcours;
    }

    private static void parcoursPrefixeRecursif(Map<String, List<String>> arbre, String sommet,
                                                Set<String> visites, List<String> parcours) {
        visites.add(sommet);
        parcours.add(sommet);

        List<String> voisins = arbre.getOrDefault(sommet, new ArrayList<>());
        for (String voisin : voisins) {
            if (!visites.contains(voisin)) {
                parcoursPrefixeRecursif(arbre, voisin, visites, parcours);
            }
        }
    }

    public static ItineraireVoyageur appliquerShortcutting(GrapheNonOriente graphe, List<String> parcours, String depart) {
        List<String> cheminOptimise = new ArrayList<>();
        Set<String> visites = new HashSet<>();
        int distanceTotale = 0;

        cheminOptimise.add(depart);
        visites.add(depart);
        String sommetActuel = depart;

        for (String sommet : parcours) {
            if (!visites.contains(sommet)) {
                int distance = graphe.getPoid(sommetActuel, sommet);

                if (distance == -1) {
                    distance = calculerPlusCourtChemin(graphe, sommetActuel, sommet);
                    if (distance == -1) {
                        System.err.println("ERREUR: Pas de chemin entre " + sommetActuel + " et " + sommet);
                        distance = 0;
                    }
                }

                cheminOptimise.add(sommet);
                visites.add(sommet);
                distanceTotale += distance;
                sommetActuel = sommet;
            }
        }

        int distanceRetour = graphe.getPoid(sommetActuel, depart);
        if (distanceRetour == -1) {
            distanceRetour = calculerPlusCourtChemin(graphe, sommetActuel, depart);
            if (distanceRetour == -1) {
                System.err.println("ERREUR: Pas de chemin de retour entre " + sommetActuel + " et " + depart);
                distanceRetour = 0;
            }
        }
        cheminOptimise.add(depart);
        distanceTotale += distanceRetour;

        return new ItineraireVoyageur(cheminOptimise, distanceTotale);
    }

    private static int calculerPlusCourtChemin(GrapheNonOriente graphe, String depart, String arrivee) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<String> filePriorite = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<String> visites = new HashSet<>();

        for (String sommet : graphe.getSommet()) {
            distances.put(sommet, Integer.MAX_VALUE);
        }
        distances.put(depart, 0);
        filePriorite.add(depart);

        while (!filePriorite.isEmpty()) {
            String sommetActuel = filePriorite.poll();

            if (sommetActuel.equals(arrivee)) {
                return distances.get(arrivee);
            }

            if (visites.contains(sommetActuel)) {
                continue;
            }
            visites.add(sommetActuel);

            Map<String, Integer> voisins = graphe.getVoisins(sommetActuel);
            for (Map.Entry<String, Integer> voisin : voisins.entrySet()) {
                String voisinNom = voisin.getKey();
                int poids = voisin.getValue();

                int nouvelleDistance = distances.get(sommetActuel) + poids;
                if (nouvelleDistance < distances.get(voisinNom)) {
                    distances.put(voisinNom, nouvelleDistance);
                    filePriorite.remove(voisinNom);
                    filePriorite.add(voisinNom);
                }
            }
        }

        return distances.get(arrivee) == Integer.MAX_VALUE ? -1 : distances.get(arrivee);
    }

    public static ItineraireVoyageur calculerItineraireMST(GrapheNonOriente graphe, String depart) {
        List<Arete> mst = kruskal(graphe);

        System.out.println("\n--- Arbre Couvrant de Poids Minimum (MST) ---");
        int poidsMST = 0;
        for (Arete arete : mst) {
            System.out.println(arete);
            poidsMST += arete.getPoids();
        }
        System.out.println("Poids total du MST: " + poidsMST + " m");
        System.out.println("Distance théorique (2 × MST): " + (2 * poidsMST) + " m\n");

        Map<String, List<String>> arbreMST = construireArbreMST(mst);

        List<String> parcours = parcoursPrefixe(arbreMST, depart);
        System.out.println("Parcours préfixe (avant shortcutting): " + parcours);

        ItineraireVoyageur itineraire = appliquerShortcutting(graphe, parcours, depart);

        return itineraire;
    }

    public static List<ItineraireVoyageur> calculerItineraireMSTAvecCapacite(
            GrapheNonOriente graphe,
            String depot,
            Map<String, Integer> contenances,
            int capaciteMax) {

        List<Arete> mst = kruskal(graphe);

        System.out.println("\n--- Arbre Couvrant de Poids Minimum (MST) ---");
        int poidsMST = 0;
        for (Arete arete : mst) {
            System.out.println(arete);
            poidsMST += arete.getPoids();
        }
        System.out.println("Poids total du MST: " + poidsMST + " m");
        System.out.println("Distance théorique (2 × MST): " + (2 * poidsMST) + " m\n");

        Map<String, List<String>> arbreMST = construireArbreMST(mst);
        List<String> parcours = parcoursPrefixe(arbreMST, depot);
        System.out.println("Parcours préfixe complet: " + parcours);

        List<ItineraireVoyageur> tournees = decouperEnTournees(graphe, parcours, depot, contenances, capaciteMax);

        return tournees;
    }

    private static List<ItineraireVoyageur> decouperEnTournees(
            GrapheNonOriente graphe,
            List<String> parcours,
            String depot,
            Map<String, Integer> contenances,
            int capaciteMax) {

        List<ItineraireVoyageur> tournees = new ArrayList<>();
        List<String> tourneeActuelle = new ArrayList<>();
        int chargeActuelle = 0;

        tourneeActuelle.add(depot);

        for (String sommet : parcours) {
            if (sommet.equals(depot)) {
                continue;
            }

            int contenance = contenances.getOrDefault(sommet, 0);

            if (chargeActuelle + contenance > capaciteMax && !tourneeActuelle.isEmpty()) {
                tourneeActuelle.add(depot);
                ItineraireVoyageur tournee = calculerDistanceTournee(graphe, tourneeActuelle);
                tournees.add(tournee);

                tourneeActuelle = new ArrayList<>();
                tourneeActuelle.add(depot);
                chargeActuelle = 0;
            }

            tourneeActuelle.add(sommet);
            chargeActuelle += contenance;
        }

        if (tourneeActuelle.size() > 1) {
            tourneeActuelle.add(depot);
            ItineraireVoyageur tournee = calculerDistanceTournee(graphe, tourneeActuelle);
            tournees.add(tournee);
        }

        return tournees;
    }

    private static ItineraireVoyageur calculerDistanceTournee(GrapheNonOriente graphe, List<String> chemin) {
        int distanceTotale = 0;

        for (int i = 0; i < chemin.size() - 1; i++) {
            String sommetActuel = chemin.get(i);
            String sommetSuivant = chemin.get(i + 1);

            int distance = graphe.getPoid(sommetActuel, sommetSuivant);

            if (distance == -1) {
                distance = calculerPlusCourtChemin(graphe, sommetActuel, sommetSuivant);
                if (distance == -1) {
                    System.err.println("ERREUR: Pas de chemin entre " + sommetActuel + " et " + sommetSuivant);
                    distance = 0;
                }
            }

            distanceTotale += distance;
        }

        return new ItineraireVoyageur(chemin, distanceTotale);
    }
}
