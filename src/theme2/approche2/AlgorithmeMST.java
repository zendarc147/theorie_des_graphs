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

        UnionTrouve uf = new UnionTrouve(graphe.getSommet());
        List<Arete> mst = new ArrayList<>();

        for (Arete arete : aretes) {
            if (uf.union(arete.getSommet1(), arete.getSommet2())) {
                mst.add(arete);
            }
        }

        return mst;
    }

    //construit un arbre à partir de la liste d'arêtes du MST
    public static Map<String, List<String>> construireArbreMST(List<Arete> mst) {
        //Map où chaque sommet a une liste de ses voisins
        Map<String, List<String>> arbre = new HashMap<>();

        //pour chaque arête du MST
        for (Arete arete : mst) {
            //on s'assure que les deux sommets existent dans la map
            arbre.putIfAbsent(arete.getSommet1(), new ArrayList<>());
            arbre.putIfAbsent(arete.getSommet2(), new ArrayList<>());
            //on ajoute chaque sommet comme voisin de l'autre (graphe non orienté)
            arbre.get(arete.getSommet1()).add(arete.getSommet2());
            arbre.get(arete.getSommet2()).add(arete.getSommet1());
        }

        return arbre;
    }

    //effectue un parcours préfixe de l'arbre MST
    public static List<String> parcoursPrefixe(Map<String, List<String>> arbre, String racine) {
        List<String> parcours = new ArrayList<>();//liste qui contiendra l'ordre de visite
        Set<String> visites = new HashSet<>();//pour éviter de visiter deux fois un sommet
        parcoursPrefixeRecursif(arbre, racine, visites, parcours);
        return parcours;
    }

    //méthode récursive pour le parcours préfixe
    private static void parcoursPrefixeRecursif(Map<String, List<String>> arbre, String sommet,Set<String> visites, List<String> parcours) {
        visites.add(sommet);//on marque le sommet comme visité
        parcours.add(sommet);//on l'ajoute au parcours

        //on récupère la liste des voisins du sommet
        List<String> voisins = arbre.getOrDefault(sommet, new ArrayList<>());
        //pour chaque voisin non visité
        for (String voisin : voisins) {
            if (!visites.contains(voisin)) {
                //on visite récursivement ce voisin
                parcoursPrefixeRecursif(arbre, voisin, visites, parcours);
            }
        }
    }

    //applique le shortcutting : saute les sommets déjà visités et prend le chemin direct
    public static ItineraireVoyageur appliquerShortcutting(GrapheNonOriente graphe, List<String> parcours, String depart) {
        List<String> cheminOptimise = new ArrayList<>();//chemin final sans doublons
        Set<String> visites = new HashSet<>();//pour suivre les sommets déjà visités
        int distanceTotale = 0;

        //on commence au point de départ
        cheminOptimise.add(depart);
        visites.add(depart);
        String sommetActuel = depart;

        //pour chaque sommet du parcours préfixe
        for (String sommet : parcours) {
            if (!visites.contains(sommet)) {//si pas encore visité
                //on cherche d'abord un lien direct
                int distance = graphe.getPoid(sommetActuel, sommet);

                //si pas de lien direct, on calcule le plus court chemin
                if (distance == -1) {
                    distance = calculerPlusCourtChemin(graphe, sommetActuel, sommet);
                    if (distance == -1) {
                        System.err.println("ERREUR: Pas de chemin entre " + sommetActuel + " et " + sommet);
                        distance = 0;
                    }
                }

                //on ajoute ce sommet au chemin optimisé
                cheminOptimise.add(sommet);
                visites.add(sommet);
                distanceTotale += distance;
                sommetActuel = sommet;
            }
            //si déjà visité, on le saute (shortcutting)
        }

        //retour au dépôt
        int distanceRetour = graphe.getPoid(sommetActuel, depart);
        if (distanceRetour == -1) {
            //si pas de lien direct pour le retour, on calcule le plus court chemin
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

    //algorithme de Dijkstra pour calculer le plus court chemin entre deux sommets
    private static int calculerPlusCourtChemin(GrapheNonOriente graphe, String depart, String arrivee) {
        //map qui stocke la distance minimale connue pour atteindre chaque sommet
        Map<String, Integer> distances = new HashMap<>();
        //file de priorité qui trie les sommets par distance croissante
        PriorityQueue<String> filePriorite = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        //ensemble des sommets déjà traités
        Set<String> visites = new HashSet<>();

        //initialisation : toutes les distances à l'infini
        for (String sommet : graphe.getSommet()) {
            distances.put(sommet, Integer.MAX_VALUE);
        }
        //sauf le départ qui est à distance 0
        distances.put(depart, 0);
        filePriorite.add(depart);

        //tant qu'il reste des sommets à traiter
        while (!filePriorite.isEmpty()) {
            String sommetActuel = filePriorite.poll();//on prend le sommet avec la plus petite distance

            //si on a atteint l'arrivée, on retourne la distance
            if (sommetActuel.equals(arrivee)) {
                return distances.get(arrivee);
            }

            //si déjà visité, on passe au suivant
            if (visites.contains(sommetActuel)) {
                continue;
            }
            visites.add(sommetActuel);

            //on examine tous les voisins du sommet actuel
            Map<String, Integer> voisins = graphe.getVoisins(sommetActuel);
            for (Map.Entry<String, Integer> voisin : voisins.entrySet()) {
                String voisinNom = voisin.getKey();
                int poids = voisin.getValue();

                //on calcule la nouvelle distance en passant par le sommet actuel
                int nouvelleDistance = distances.get(sommetActuel) + poids;
                //si cette distance est meilleure, on la met à jour
                if (nouvelleDistance < distances.get(voisinNom)) {
                    distances.put(voisinNom, nouvelleDistance);
                    filePriorite.remove(voisinNom);//on retire l'ancienne version
                    filePriorite.add(voisinNom);//on ajoute avec la nouvelle distance
                }
            }
        }

        //si l'arrivée n'a pas été atteinte, on retourne -1, sinon la distance finale
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

    //calcule l'itinéraire MST avec contrainte de capacité (plusieurs tournées)
    public static List<ItineraireVoyageur> calculerItineraireMSTAvecCapacite(
            GrapheNonOriente graphe,
            String depot,
            Map<String, Integer> contenances,//charge de chaque point
            int capaciteMax) {//capacité maximale du camion

        //calcul du MST avec Kruskal
        List<Arete> mst = kruskal(graphe);

        System.out.println("\n--- Arbre Couvrant de Poids Minimum (MST) ---");
        int poidsMST = 0;
        for (Arete arete : mst) {
            System.out.println(arete);
            poidsMST += arete.getPoids();
        }
        System.out.println("Poids total du MST: " + poidsMST + " m");
        System.out.println("Distance théorique (2 × MST): " + (2 * poidsMST) + " m\n");

        //construction de l'arbre et parcours préfixe
        Map<String, List<String>> arbreMST = construireArbreMST(mst);
        List<String> parcours = parcoursPrefixe(arbreMST, depot);
        System.out.println("Parcours préfixe complet: " + parcours);

        //découpage en plusieurs tournées selon la capacité
        List<ItineraireVoyageur> tournees = decouperEnTournees(graphe, parcours, depot, contenances, capaciteMax);

        return tournees;
    }

    //découpe le parcours en plusieurs tournées en respectant la capacité maximale
    private static List<ItineraireVoyageur> decouperEnTournees(
            GrapheNonOriente graphe,
            List<String> parcours,//ordre de visite des sommets
            String depot,//point de départ et retour
            Map<String, Integer> contenances,//charge de chaque sommet
            int capaciteMax) {//capacité max du camion

        List<ItineraireVoyageur> tournees = new ArrayList<>();//liste des tournées
        List<String> tourneeActuelle = new ArrayList<>();//tournée en cours de construction
        int chargeActuelle = 0;//charge accumulée dans la tournée actuelle

        tourneeActuelle.add(depot);//chaque tournée commence au dépôt

        //pour chaque sommet du parcours
        for (String sommet : parcours) {
            if (sommet.equals(depot)) {//on ignore le dépôt dans le parcours
                continue;
            }

            int contenance = contenances.getOrDefault(sommet, 0);

            //si ajouter ce sommet dépasse la capacité, on termine la tournée actuelle
            if (chargeActuelle + contenance > capaciteMax && !tourneeActuelle.isEmpty()) {
                tourneeActuelle.add(depot);//retour au dépôt
                ItineraireVoyageur tournee = calculerDistanceTournee(graphe, tourneeActuelle);
                tournees.add(tournee);//on sauvegarde cette tournée

                //on commence une nouvelle tournée
                tourneeActuelle = new ArrayList<>();
                tourneeActuelle.add(depot);
                chargeActuelle = 0;
            }

            //on ajoute le sommet à la tournée actuelle
            tourneeActuelle.add(sommet);
            chargeActuelle += contenance;
        }

        //on n'oublie pas la dernière tournée
        if (tourneeActuelle.size() > 1) {//si elle contient au moins un point en plus du dépôt
            tourneeActuelle.add(depot);//retour au dépôt
            ItineraireVoyageur tournee = calculerDistanceTournee(graphe, tourneeActuelle);
            tournees.add(tournee);
        }

        return tournees;
    }

    //calcule la distance totale d'une tournée donnée
    private static ItineraireVoyageur calculerDistanceTournee(GrapheNonOriente graphe, List<String> chemin) {
        int distanceTotale = 0;

        //pour chaque paire de sommets consécutifs dans le chemin
        for (int i = 0; i < chemin.size() - 1; i++) {
            String sommetActuel = chemin.get(i);
            String sommetSuivant = chemin.get(i + 1);

            //on cherche d'abord un lien direct
            int distance = graphe.getPoid(sommetActuel, sommetSuivant);

            //si pas de lien direct, on utilise Dijkstra
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
