package theme3.hypothese1;

/**
 * Classe représentant un secteur géographique de la commune
 */
public class Secteur {
    private int id;
    private String nom;
    private int couleur; // Représente le jour de collecte (-1 si pas encore assigné)

    /**
     * Constructeur
     * @param id Identifiant unique du secteur
     * @param nom Nom du secteur
     */
    public Secteur(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.couleur = -1; // Pas encore colorié
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    /**
     * Vérifie si le secteur a déjà été colorié
     * @return true si colorié, false sinon
     */
    public boolean estColorie() {
        return couleur != -1;
    }

    @Override
    public String toString() {
        String jourTexte = couleur == -1 ? "Non assigné" : "Jour " + (couleur + 1);
        return "Secteur " + id + " (" + nom + ") - " + jourTexte;
    }
}
