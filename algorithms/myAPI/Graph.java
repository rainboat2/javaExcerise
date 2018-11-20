package myAPI;

import java.util.LinkedList;

public class Graph {

    public static Graph getGraph(){
        Graph g = new Graph(13);
        int[] v = new int[] {0, 4, 0, 9, 6, 5, 0, 11, 9, 0, 7, 9, 5};
        int[] w = new int[] {5, 3, 1, 12, 4, 4, 2, 12, 10, 6, 8, 11, 3};
        for (int i = 0; i < v.length; i++)
            g.addEdge(v[i], w[i]);
        return g;
    }

    private final int V;                //顶点的数目
    private int E;                      //边的数目
    private LinkedList<Integer>[] adj;  //邻接表


    @SuppressWarnings("unchecked")
    public Graph(int V){
        this.V = V;
        E = 0;
        adj = (LinkedList<Integer>[]) new LinkedList[V];
        for (int i = 0; i < V; i++)
            adj[i] = new LinkedList<>();
    }

    public int V() { return V;}
    public int E() { return E;}
    public void addEdge(int v, int w){
        adj[v].add(w);
        adj[w].add(v);
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }
}
