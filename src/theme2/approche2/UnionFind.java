package theme2.approche2;

import java.util.*;

public class UnionFind {
    private Map<String, String> parent;
    private Map<String, Integer> rang;

    public UnionFind(Set<String> sommets) {
        parent = new HashMap<>();
        rang = new HashMap<>();

        for (String sommet : sommets) {
            parent.put(sommet, sommet);
            rang.put(sommet, 0);
        }
    }

    public String find(String sommet) {
        if (!parent.get(sommet).equals(sommet)) {
            parent.put(sommet, find(parent.get(sommet)));
        }
        return parent.get(sommet);
    }

    public boolean union(String sommet1, String sommet2) {
        String racine1 = find(sommet1);
        String racine2 = find(sommet2);

        if (racine1.equals(racine2)) {
            return false;
        }

        int rang1 = rang.get(racine1);
        int rang2 = rang.get(racine2);

        if (rang1 < rang2) {
            parent.put(racine1, racine2);
        } else if (rang1 > rang2) {
            parent.put(racine2, racine1);
        } else {
            parent.put(racine2, racine1);
            rang.put(racine1, rang1 + 1);
        }

        return true;
    }
}
