package theme1.prob1.Hyp2.H02;

public class Maison2 {

    public int depart;
    public int arrivee;
    public boolean coteG;
    public boolean verif;

    public Maison2(int depart, int arrivee, boolean coteG) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.coteG = coteG;
        this.verif = false;
    }
    @Override
    public String toString() {
        return "(" + depart + "->" + arrivee + ", coteG = "+coteG+")";
    }
}
