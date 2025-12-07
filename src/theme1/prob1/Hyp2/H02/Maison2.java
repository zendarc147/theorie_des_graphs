package src.theme1.prob1.Hyp2.H02;

public class Maison2 {

    //sommet depart rue de la maison
    public int depart;
    //sommet arrivee rue de la maison
    public int arrivee;
    //true si maison du cote gauche, cote droit sinon
    public boolean coteG;
    //true si non ramassable (ou deja ramasse)
    public boolean verif;

    //constructeur
    public Maison2(int depart, int arrivee, boolean coteG) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.coteG = coteG;
        this.verif = false;
    }
    //aide chatgpt pour regler un probleme de String
    @Override
    public String toString() {
        return "(" + depart + "->" + arrivee + ", coteG = "+coteG+")";
    }
}
