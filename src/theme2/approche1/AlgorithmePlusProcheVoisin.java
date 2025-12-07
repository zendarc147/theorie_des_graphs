package theme2.approche1;

import java.util.*;

public class AlgorithmePlusProcheVoisin {

    public static ItineraireVoyageur calculerItineraire(GrapheNonOriente graphe, String sommetDepart) {
        List<String> chemin = new ArrayList<>();
        Set<String> visite = new HashSet<>();
        int totalDistance = 0;

        String som_actuel = sommetDepart;//on commence au sommet de depart
        chemin.add(som_actuel);
        visite.add(som_actuel);

        while (visite.size() < graphe.getSommet().size()) {
            //tant que le nombre de sommets visites est plus petit que le nombre de sommet du graphe
            String som_proche = null;
            int minDistance = 100000;//grande valeur pour trouver forcément une valeur plus petite

            String[] sommetsArray = graphe.getSommet().toArray(new String[0]);//transforme en tableau
            for (int i = 0; i < sommetsArray.length; i++) {
                String candidat = sommetsArray[i];
                if (!visite.contains(candidat)) {//s'il n'y as pas le candidat dans les sommets visités
                    int distanceDirecte = graphe.getPoid(som_actuel, candidat);
                    //alors distane = le poids entre les sommets

                    if (distanceDirecte != -1 && distanceDirecte < minDistance) {
                    //si la distance est plus petite que le minimum de distance
                        minDistance = distanceDirecte;
                        som_proche = candidat;
                        //alors on remplace la distance minimum
                    }
                }
            }

            //si aucun voisin direct n'a été trouvé, on utilise Dijkstra
            if (som_proche == null) {
                //on parcourt tous les sommets non visités
                for (String candidat : graphe.getSommet()) {
                    if (!visite.contains(candidat)) {
                        //on calcule la distance avec Dijkstra (plus court chemin indirect)
                        int distanceDijkstra = dijkstra(graphe, som_actuel, candidat, new HashSet<>());

                        //si on trouve un chemin et qu'il est plus court que le minimum actuel
                        if (distanceDijkstra != -1 && distanceDijkstra < minDistance) {
                            minDistance = distanceDijkstra;
                            som_proche = candidat;
                        }
                    }
                }

                //si on a trouvé un sommet accessible via Dijkstra
                if (som_proche != null) {
                    //on récupère le chemin complet vers ce sommet
                    List<String> cheminIntermédiaire = obtenirCheminDijkstra(graphe, som_actuel, som_proche, new HashSet<>());
                    //on ajoute tous les sommets intermédiaires au chemin (sauf le premier qui est déjà présent)
                    for (int i = 1; i < cheminIntermédiaire.size(); i++) {
                        chemin.add(cheminIntermédiaire.get(i));
                    }
                    visite.add(som_proche);
                    totalDistance += minDistance;
                    som_actuel = som_proche;
                    continue;//on passe à l'itération suivante
                }
            } else {
                //cas où on a trouvé un voisin direct
                chemin.add(som_proche);
                visite.add(som_proche);
                totalDistance += minDistance;
                som_actuel = som_proche;
            }

            //dernier recours : si aucun chemin n'a été trouvé, on prend n'importe quel sommet non visité
            if (som_proche == null) {
                for (String candidat : graphe.getSommet()) {
                    if (!visite.contains(candidat)) {
                        som_proche = candidat;
                        chemin.add(som_proche);
                        visite.add(som_proche);
                        som_actuel = som_proche;
                        break;//on prend le premier trouvé
                    }
                }
                //si vraiment aucun sommet n'est accessible, on arrête
                if (som_proche == null) {
                    break;
                }
            }
        }

        //retour au point de départ
        int returnDistance = graphe.getPoid(som_actuel, sommetDepart);
        if (returnDistance == -1) {
            //si pas de lien direct, on utilise Dijkstra pour le retour
            returnDistance = dijkstra(graphe, som_actuel, sommetDepart, new HashSet<>());
            if (returnDistance != -1) {
                //on récupère le chemin de retour complet
                List<String> cheminRetour = obtenirCheminDijkstra(graphe, som_actuel, sommetDepart, new HashSet<>());
                //on ajoute tous les sommets du chemin de retour
                for (int i = 1; i < cheminRetour.size(); i++) {
                    chemin.add(cheminRetour.get(i));
                }
                totalDistance += returnDistance;
            }
        } else {
            //lien direct vers le départ : on ajoute simplement le sommet de départ
            chemin.add(sommetDepart);
            totalDistance += returnDistance;
        }

        return new ItineraireVoyageur(chemin, totalDistance);
    }

    public static ItineraireVoyageur calculerItineraireAvecDijkstra(GrapheNonOriente graphe, String sommetDepart) {
        List<String> chemin = new ArrayList<>();
        Set<String> visite = new HashSet<>();
        int totalDistance = 0;

        String som_actuel = sommetDepart;
        chemin.add(som_actuel);
        visite.add(som_actuel);

        while (visite.size() < graphe.getSommet().size()) {
            String som_proche = null;
            int minDistance = Integer.MAX_VALUE;

            for (String candidat : graphe.getSommet()) {
                if (!visite.contains(candidat)) {
                    int distance = dijkstra(graphe, som_actuel, candidat, visite);

                    if (distance < minDistance && distance != -1) {
                        minDistance = distance;
                        som_proche = candidat;
                    }
                }
            }

            if (som_proche == null) {
                System.out.println("Erreur: Impossible de trouver un chemin vers tous les sommets!");
                break;
            }

            List<String> cheminVersVoisin = obtenirCheminDijkstra(graphe, som_actuel, som_proche, visite);

            for (int i = 1; i < cheminVersVoisin.size(); i++) {
                chemin.add(cheminVersVoisin.get(i));
            }

            visite.add(som_proche);
            totalDistance += minDistance;
            som_actuel = som_proche;
        }

        int returnDistance = dijkstra(graphe, som_actuel, sommetDepart, new HashSet<>());
        if (returnDistance != -1) {
            List<String> cheminRetour = obtenirCheminDijkstra(graphe, som_actuel, sommetDepart, new HashSet<>());
            for (int i = 1; i < cheminRetour.size(); i++) {
                chemin.add(cheminRetour.get(i));
            }
            totalDistance += returnDistance;
        }

        return new ItineraireVoyageur(chemin, totalDistance);
    }

    private static int dijkstra(GrapheNonOriente graphe, String depart, String arrivee, Set<String> sommetsInterdits) {
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

                if (sommetsInterdits.contains(voisinNom) && !voisinNom.equals(arrivee)) {
                    continue;
                }

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

    private static List<String> obtenirCheminDijkstra(GrapheNonOriente graphe, String depart, String arrivee, Set<String> sommetsInterdits) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecesseurs = new HashMap<>();
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
                break;
            }

            if (visites.contains(sommetActuel)) {
                continue;
            }
            visites.add(sommetActuel);

            Map<String, Integer> voisins = graphe.getVoisins(sommetActuel);
            for (Map.Entry<String, Integer> voisin : voisins.entrySet()) {
                String voisinNom = voisin.getKey();
                int poids = voisin.getValue();

                if (sommetsInterdits.contains(voisinNom) && !voisinNom.equals(arrivee)) {
                    continue;
                }

                int nouvelleDistance = distances.get(sommetActuel) + poids;
                if (nouvelleDistance < distances.get(voisinNom)) {
                    distances.put(voisinNom, nouvelleDistance);
                    predecesseurs.put(voisinNom, sommetActuel);
                    filePriorite.remove(voisinNom);
                    filePriorite.add(voisinNom);
                }
            }
        }

        List<String> chemin = new ArrayList<>();
        String sommet = arrivee;
        while (sommet != null) {
            chemin.add(0, sommet);
            sommet = predecesseurs.get(sommet);
        }

        return chemin;
    }
}
