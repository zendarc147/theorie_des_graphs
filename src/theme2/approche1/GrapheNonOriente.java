package theme2.approche1;

import java.io.*;
import java.util.*;

public class GrapheNonOriente {
    private Map<String, Map<String, Integer>> arcListe;
    private Set<String> sommet;

    public GrapheNonOriente() {
        arcListe = new HashMap<>();
        sommet = new HashSet<>();
    }

    public void addSommet(String som) {
        sommet.add(som);
        arcListe.putIfAbsent(som, new HashMap<>());
    }

    public void addArc(String source, String destination, int poids) {
        addSommet(source);
        addSommet(destination);
        arcListe.get(source).put(destination, poids);
        arcListe.get(destination).put(source, poids);
    }

    public int getPoid(String source, String destination) {
        if (arcListe.containsKey(source) && arcListe.get(source).containsKey(destination)) {
            //si arcliste contient la source et si la map des voisins de source contient la destination
            return arcListe.get(source).get(destination);
        }
        return -1;
    }

    public Set<String> getSommet() {
        return sommet;
    }

    public Map<String, Integer> getVoisins(String som) {
        if (arcListe.containsKey(som)) {//si le sommet est dans arcliste
          return arcListe.get(som);//alors on retourne la map des voisins
        } else {
         return new HashMap<>();//sinon retourne une hashmap vide
        }
    }

    public void chargerFichier(String nomf) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomf));

        int nbrSommets = Integer.parseInt(br.readLine().trim());
        int oriente = Integer.parseInt(br.readLine().trim());

        String ligne;
        while ((ligne = br.readLine()) != null) {
            ligne = ligne.trim(); //supprimes les espaces blancs au début et à la fin
            if (ligne.isEmpty()) continue; //permet de sauter les lignes vides

            String[] tab = ligne.split("\\s+"); // divise chaine en 1 tableau de sous-chaines
            // \\s+ signifie un ou plusieurs caractères d'espaces vides ou blancs
            if (tab.length == 3) {
                String som1 = tab[0];
                String som2 = tab[1];
                int poids = Integer.parseInt(tab[2]); //convertit le poids de string à int
                addArc(som1, som2, poids); //ajoute l'arrete au graphe
            }
        }
        br.close();
    }
}
