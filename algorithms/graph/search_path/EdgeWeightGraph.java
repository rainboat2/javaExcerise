package graph.search_path;

import graph.Edge;

import java.util.LinkedList;

public class EdgeWeightGraph {

    private int V;
    private int E;
    private LinkedList<Edge>[] adj;

    public EdgeWeightGraph(int V){
        this.V = V;
        adj = (LinkedList<Edge>[]) new LinkedList[V];
        for (int i = 0; i < V; i++)
            adj[i] = new LinkedList<>();
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public void addEdge(Edge e){
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> getAdj(int v){
        return adj[v];
    }

    public Iterable<Edge> edges(){

    }
}
