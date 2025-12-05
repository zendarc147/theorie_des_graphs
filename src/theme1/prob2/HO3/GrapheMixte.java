package theme1.prob2.HO3;

import java.util.*;

public class GrapheMixte {
    private int nbSommets;
    private Map<Integer, List<Integer>> listeAdjacence;
    private Map<Integer, Integer> degresEntrants;
    private Map<Integer, Integer> degresSortants;

    // Pour distinguer les types d'arêtes
    private Set<String> aretesNonOrientees;  // RUE (1 voie)
    private Set<String> aretesDoubles;       // RUE_DOUBLE (plusieurs voies)
    // Les arêtes orientées sont juste dans listeAdjacence

    public GrapheMixte(int nbSommets) {
        this.nbSommets = nbSommets;
        this.listeAdjacence = new HashMap<>();
        this.degresEntrants = new HashMap<>();
        this.degresSortants = new HashMap<>();
        this.aretesNonOrientees = new HashSet<>();
        this.aretesDoubles = new HashSet<>();

        for (int i = 0; i < nbSommets; i++) {
            listeAdjacence.put(i, new ArrayList<>());
            degresEntrants.put(i, 0);
            degresSortants.put(i, 0);
        }
    }

    // RUE : double sens, 1 voie (ramassage des 2 côtés en 1 passage)
    public void ajouterAreteNonOrientee(int sommet1, int sommet2) {
        listeAdjacence.get(sommet1).add(sommet2);
        listeAdjacence.get(sommet2).add(sommet1);

        String cle1 = Math.min(sommet1, sommet2) + "-" + Math.max(sommet1, sommet2);
        aretesNonOrientees.add(cle1);

        // Pour le cas non orienté, on compte deg_in et deg_out pour les deux
        degresSortants.put(sommet1, degresSortants.get(sommet1) + 1);
        degresEntrants.put(sommet1, degresEntrants.get(sommet1) + 1);
        degresSortants.put(sommet2, degresSortants.get(sommet2) + 1);
        degresEntrants.put(sommet2, degresEntrants.get(sommet2) + 1);
    }

    // RUE_DOUBLE : double sens, plusieurs voies (2 passages nécessaires)
    public void ajouterAreteDouble(int sommet1, int sommet2) {
        listeAdjacence.get(sommet1).add(sommet2);
        listeAdjacence.get(sommet2).add(sommet1);

        String cle = Math.min(sommet1, sommet2) + "-" + Math.max(sommet1, sommet2);
        aretesDoubles.add(cle);

        // Chaque direction est un arc séparé
        degresSortants.put(sommet1, degresSortants.get(sommet1) + 1);
        degresEntrants.put(sommet2, degresEntrants.get(sommet2) + 1);
        degresSortants.put(sommet2, degresSortants.get(sommet2) + 1);
        degresEntrants.put(sommet1, degresEntrants.get(sommet1) + 1);
    }

    // RUE_ORIENTEE : sens unique
    public void ajouterAreteOrientee(int sommetDepart, int sommetArrivee) {
        listeAdjacence.get(sommetDepart).add(sommetArrivee);
        degresSortants.put(sommetDepart, degresSortants.get(sommetDepart) + 1);
        degresEntrants.put(sommetArrivee, degresEntrants.get(sommetArrivee) + 1);
    }

    public int getDegreeEntrant(int sommet) {
        return degresEntrants.get(sommet);
    }

    public int getDegreeSortant(int sommet) {
        return degresSortants.get(sommet);
    }

    public List<Integer> getVoisins(int sommet) {
        return listeAdjacence.get(sommet);
    }

    public int getNbSommets() {
        return nbSommets;
    }

    public Map<Integer, List<Integer>> getListeAdjacence() {
        return listeAdjacence;
    }

    public boolean estAreteNonOrientee(int s1, int s2) {
        String cle = Math.min(s1, s2) + "-" + Math.max(s1, s2);
        return aretesNonOrientees.contains(cle);
    }

    public boolean estAreteDouble(int s1, int s2) {
        String cle = Math.min(s1, s2) + "-" + Math.max(s1, s2);
        return aretesDoubles.contains(cle);
    }

    public void afficherGraphe() {
        System.out.println("\n=== Structure du graphe mixte ===");
        for (int i = 0; i < nbSommets; i++) {
            System.out.print("Sommet " + i + " (deg_in=" + degresEntrants.get(i)
                           + ", deg_out=" + degresSortants.get(i) + ") : ");
            System.out.print("successeurs = " + listeAdjacence.get(i));
            System.out.println();
        }

        System.out.println("\nTypes d'arêtes :");
        System.out.println("  - Arêtes non orientées (1 voie) : " + aretesNonOrientees.size());
        System.out.println("  - Arêtes doubles (plusieurs voies) : " + aretesDoubles.size());
        int nbOrientees = 0;
        for (List<Integer> voisins : listeAdjacence.values()) {
            nbOrientees += voisins.size();
        }
        nbOrientees -= (aretesNonOrientees.size() * 2 + aretesDoubles.size() * 2);
        System.out.println("  - Arêtes orientées : " + nbOrientees);
    }
}