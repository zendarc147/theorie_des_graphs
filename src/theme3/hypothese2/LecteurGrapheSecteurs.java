package theme3.hypothese2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe pour lire un graphe de secteurs depuis un fichier texte
 */
public class LecteurGrapheSecteurs {

    /**
     * Lit un graphe de secteurs depuis un fichier
     *
     * Format du fichier :
     * SECTEURS: n
     * SECTEUR id nom quantite
     * ...
     * VOISINS id1 id2
     * ...
     *
     * @param nomFichier Le chemin du fichier à lire
     * @return Le graphe de secteurs construit
     * @throws IOException Si erreur de lecture
     */
    public static GrapheSecteurs lireGraphe(String nomFichier) throws IOException {
        GrapheSecteurs graphe = new GrapheSecteurs();
        BufferedReader reader = new BufferedReader(new FileReader(nomFichier));

        String ligne;
        while ((ligne = reader.readLine()) != null) {
            // Ignorer les commentaires et les lignes vides
            ligne = ligne.trim();
            if (ligne.isEmpty() || ligne.startsWith("#")) {
                continue;
            }

            // Diviser la ligne en mots
            String[] parties = ligne.split("\\s+", 4);

            if (parties[0].equals("SECTEURS:")) {
                // Ligne SECTEURS: n
                int nbSecteurs = Integer.parseInt(parties[1]);
                System.out.println("Lecture d'un graphe avec " + nbSecteurs + " secteurs");

            } else if (parties[0].equals("SECTEUR")) {
                // Ligne SECTEUR id nom quantite
                int id = Integer.parseInt(parties[1]);
                String nom = parties[2];
                int quantite = Integer.parseInt(parties[3]);
                Secteur secteur = new Secteur(id, nom, quantite);
                graphe.ajouterSecteur(secteur);
                System.out.println("  Secteur ajouté : " + nom + " (ID:" + id + ", Déchets:" + quantite + ")");

            } else if (parties[0].equals("VOISINS")) {
                // Ligne VOISINS id1 id2
                int id1 = Integer.parseInt(parties[1]);
                int id2 = Integer.parseInt(parties[2]);
                graphe.ajouterVoisinage(id1, id2);
                System.out.println("  Voisinage ajouté : " + id1 + " <-> " + id2);
            }
        }

        reader.close();
        System.out.println("Graphe chargé avec succès !\n");
        return graphe;
    }
}
