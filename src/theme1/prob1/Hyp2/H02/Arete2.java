package theme1.prob1.Hyp2.H02;

public class Arete2 {

    // destination de l'arete
    public int destination;
    //son poids : sa distance
    public int distance;
    //true si non-oriente false sinon
    public boolean doubleSens;

    //constructeur
    public Arete2(int destination, int distance, boolean doubleSens) {
        this.destination = destination;
        this.distance = distance;
        this.doubleSens = doubleSens;
    }
}
