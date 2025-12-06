package src.theme1.prob1.Hyp1.H03;

public class Maison3 {

    //sommet de depart arête de la maison
    public int depart;
    //sommet d'arrivee arête de la maison
    public int arrive;
    // pour savoir le coté de la maison sur la rue (vrai = cote gauche faux= cote droit)
    public boolean coteG;
    //sommet virtuel ds le graphe
    public int id;
    //possible de passer par la maison?
    public int verif;

    //constructeur
    public Maison3(int depart, int arrive, boolean coteG) {
        this.depart = depart;
        this.arrive = arrive;
        this.coteG = coteG;
        verif =0;
    }
}
