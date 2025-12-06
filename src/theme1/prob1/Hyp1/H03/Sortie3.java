package src.theme1.prob1.Hyp1.H03;

public class Sortie3 {

    //numéro du sommet voisin
    public int numr;
    //poids de la rue : distance jusqu'au sommet voisin
    public int poids;
    //rues à double sens
    public boolean doublesens;
    //nb de voies de la route
    public int voies;

    //constructeur
    public Sortie3(int numr, int poids, boolean doublesens, int voies) {
        this.numr = numr;
        this.poids = poids;
        this.doublesens = doublesens;
        this.voies = voies;
    }
}
