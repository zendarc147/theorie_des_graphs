package theme1.prob1.Hyp1.H01;

public class DijkstraDonnees1 {
    //la plus petite distance de source vers sommets
    public int [] distance;
    // predecesseur du sommet actuel (sur le PCC)
    public int [] pred;

    //constructeur
    public DijkstraDonnees1(int [] distance, int [] pred) {
        this.distance = distance;
        this.pred = pred;
    }
}
