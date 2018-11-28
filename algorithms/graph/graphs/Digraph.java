package graph.graphs;

import java.util.LinkedList;

public class Digraph {

    public static Digraph getDigraph(){
        Digraph d = new Digraph(13);
        int[] s = new int[]{4, 2, 3, 6, 0, 2, 11, 12, 9, 9, 8, 10, 11, 4, 3, 7, 8, 5, 0, 6, 6, 7};
        int[] t = new int[]{2, 3, 2, 0, 1, 0, 12, 9, 10, 11, 9, 12, 4, 3, 5, 8, 7, 4, 5, 4, 9, 6};
        for (int i = 0; i < s.length; i++)
            d.addEdge(s[i], t[i]);
        return d;
    }

    private int V;
    private int E;
    private LinkedList<Integer>[] adj;

    public Digraph(int V){
        this.V = V;
        adj = (LinkedList<Integer>[]) new LinkedList[V];
        for (int i = 0; i < V; i++)
            adj[i] = new LinkedList<>();
    }

    public int V(){ return V;}
    public int E(){ return E;}

    public void addEdge(int v, int w){
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> getAdj(int v){
        return adj[v];
    }

    public Digraph reverse(){
        Digraph d = new Digraph(V);
        for (int v = 0; v < V; v++)
            for (int w : getAdj(v))
                d.addEdge(w, v);   // 将所有边读取出来，逆转后加入新的图中
        return d;
    }
}
