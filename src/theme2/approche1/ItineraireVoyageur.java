package theme2.approche1;

import java.util.*;

public class ItineraireVoyageur {
    private List<String> chemin;
    private int totaleDistance;

    public ItineraireVoyageur(List<String> c, int tDistance) {
        this.chemin = c;
        this.totaleDistance = tDistance;
    }

    public List<String> getChemin() {
        return chemin;
    }

    public int getTotaleDistance() {
        return totaleDistance;
    }

    @Override
    public String toString() {
        //méthode qui va afficher clairement le chemin parcouru
        StringBuilder sb = new StringBuilder();
        //StringBuilder permets de ne pas recréer un nouvel objet string à chaque fois que l'on veut afficher
        // il va juste modifier le string au lieu d'en créer un nouveau à chaque fois
        sb.append("Itinéraire du camion:\n");
        for (int i = 0; i < chemin.size(); i++) {
            sb.append(chemin.get(i));
            if (i < chemin.size() - 1) {
                sb.append(" -> ");
            }
        }
        sb.append("\n\nDistance totale parcourue: ").append(totaleDistance).append(" m");
        return sb.toString();
    }

    public void afficherDetails(GrapheNonOriente graphe) {
        System.out.println("\nDétail des étapes:");
        for (int i = 0; i < chemin.size() - 1; i++) {
            String de = chemin.get(i);
            String vers = chemin.get(i + 1);
            int distance = graphe.getPoid(de, vers);
            System.out.println((i + 1) + ". " + de + " -> " + vers + " : " + distance + " km");
        }
    }
}
