package theme3.hypothese1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe représentant un graphe de secteurs
 * Chaque sommet est un secteur
 * Une arête entre deux secteurs signifie qu'ils sont géographiquement voisins
 */
public class GrapheSecteurs {
    private List<Secteur> secteurs;
    // Map : id du secteur -> liste des ids des secteurs voisins
    private Map<Integer, List<Integer>> adjacence;

    /**
     * Constructeur
     */
    public GrapheSecteurs() {
        this.secteurs = new ArrayList<>();
        this.adjacence = new HashMap<>();
    }

    /**
     * Ajoute un secteur au graphe
     * @param secteur Le secteur à ajouter
     */
    public void ajouterSecteur(Secteur secteur) {
        secteurs.add(secteur);
        adjacence.put(secteur.getId(), new ArrayList<>());
    }

    /**
     * Ajoute une relation de voisinage entre deux secteurs
     * @param idSecteur1 ID du premier secteur
     * @param idSecteur2 ID du second secteur
     */
    public void ajouterVoisinage(int idSecteur1, int idSecteur2) {
        // Ajouter l'arête dans les deux sens (graphe non orienté)
        adjacence.get(idSecteur1).add(idSecteur2);
        adjacence.get(idSecteur2).add(idSecteur1);
    }

    /**
     * Récupère un secteur par son ID
     * @param id L'identifiant du secteur
     * @return Le secteur correspondant, ou null si non trouvé
     */
    public Secteur getSecteur(int id) {
        for (Secteur s : secteurs) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    /**
     * Obtient la liste de tous les secteurs
     * @return La liste des secteurs
     */
    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    /**
     * Obtient la liste des voisins d'un secteur
     * @param idSecteur L'identifiant du secteur
     * @return La liste des IDs des secteurs voisins
     */
    public List<Integer> getVoisins(int idSecteur) {
        return adjacence.get(idSecteur);
    }

    /**
     * Calcule le degré d'un secteur (nombre de voisins)
     * @param idSecteur L'identifiant du secteur
     * @return Le nombre de secteurs voisins
     */
    public int getDegre(int idSecteur) {
        return adjacence.get(idSecteur).size();
    }

    /**
     * Affiche le graphe
     */
    public void afficher() {
        System.out.println("\n=== GRAPHE DES SECTEURS ===");
        System.out.println("Nombre de secteurs : " + secteurs.size());
        System.out.println("\nListe des secteurs et leurs voisins :");

        for (Secteur s : secteurs) {
            System.out.print(s.getNom() + " (ID:" + s.getId() + ") -> voisins : ");
            List<Integer> voisins = getVoisins(s.getId());
            for (int i = 0; i < voisins.size(); i++) {
                Secteur voisin = getSecteur(voisins.get(i));
                System.out.print(voisin.getNom());
                if (i < voisins.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" (degré = " + getDegre(s.getId()) + ")");
        }
    }

    /**
     * Affiche le planning de collecte (résultat de la coloration)
     */
    public void afficherPlanning() {
        System.out.println("\n=== PLANNING DE COLLECTE ===");

        // Trouver le nombre de couleurs utilisées
        int nbJours = 0;
        for (Secteur s : secteurs) {
            if (s.getCouleur() > nbJours) {
                nbJours = s.getCouleur();
            }
        }
        nbJours++; // Car les couleurs commencent à 0

        System.out.println("Nombre de jours nécessaires : " + nbJours);
        System.out.println();

        // Afficher les secteurs par jour
        for (int jour = 0; jour < nbJours; jour++) {
            System.out.println("JOUR " + (jour + 1) + " :");
            for (Secteur s : secteurs) {
                if (s.getCouleur() == jour) {
                    System.out.println("  - " + s.getNom() + " (ID:" + s.getId() + ")");
                }
            }
        }
    }
}
