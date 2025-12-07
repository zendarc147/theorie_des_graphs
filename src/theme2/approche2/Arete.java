package theme2.approche2;

public class Arete implements Comparable<Arete> {
    private String sommet1;
    private String sommet2;
    private int poids;

    public Arete(String s1, String s2, int p) {
        this.sommet1 = s1;
        this.sommet2 = s2;
        this.poids = p;
    }

    public String getSommet1() {
        return sommet1;
    }

    public String getSommet2() {
        return sommet2;
    }

    public int getPoids() {
        return poids;
    }

    @Override
    public int compareTo(Arete autre) {
        return Integer.compare(this.poids, autre.poids);
    }

    @Override
    public String toString() {
        return sommet1 + " -- " + sommet2 + " (" + poids + ")";
    }
}
