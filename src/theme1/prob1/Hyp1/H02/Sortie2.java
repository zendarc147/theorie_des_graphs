package theme1.prob1.Hyp1.H02;

public class Sortie2 {
    //numéro du sommet voisin
    public int numr;
    //poids de la rue : distance jusqu'au sommet voisin
    public int poids;
    //rue à double sens ou non
    public boolean doublesens;
    //nombre de voies (forcement 1 pour sens unique et 2 pour double sens)
    public int voies;


    //constructeur
    public Sortie2(int numr, int poids, boolean doublesens, int voies) {
        this.numr = numr;
        this.poids = poids;
        this.doublesens = doublesens;
        this.voies = voies;
    }
}
