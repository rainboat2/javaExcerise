package myAPI;

import java.util.LinkedList;

public class Graph {

    private final int V;
    private int E;
    private LinkedList<Integer>[] adj;

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
