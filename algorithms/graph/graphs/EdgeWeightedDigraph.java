package graph.graphs;

import java.util.LinkedList;

public class EdgeWeightedDigraph {

    public static EdgeWeightedDigraph getGraph(){
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(8);
        int[] v = new int[]{4, 5, 4, 5, 7, 5, 0, 0, 7, 1, 2, 6, 3, 6, 6, 0, 2, 7, 3};
        int[] w = new int[]{5, 4, 7, 7, 5, 1, 4, 2, 3, 3, 7, 2, 6, 0, 4, 2, 7, 3, 6};
        double[] weight = new double[]{0.35, 0.35, 0.37, 0.28, 0.28, 0.32, 0.38, 0.26, 0.39, 0.29, 0.34, 0.40, 0.52,
                                        0.58, 0.93, 0.26, 0.34, 0.39, 0.52};
        for (int i = 0; i < v.length; i++)
            g.addEdge(new DirectedEdge(v[i], w[i], weight[i]));
        return g;
    }

    private int V;
    private int E;
    private LinkedList<DirectedEdge>[] adj;

    public EdgeWeightedDigraph(int V){
        this.V = V;
        adj = (LinkedList<DirectedEdge>[]) new LinkedList[V];
        for (int i = 0; i < V; i++)
            adj[i] = new LinkedList<>();
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public void addEdge(DirectedEdge e){
        int v = e.from();
        adj[v].add(e);
        E++;
    }

    public Iterable<DirectedEdge> getAdj(int v){
        return adj[v];
    }

    public Iterable<DirectedEdge> edges(){
        LinkedList<DirectedEdge> edges = new LinkedList<>();
        for (int i = 0; i < V; i++)
            for (DirectedEdge e : getAdj(i))
                edges.add(e);
        return edges;
    }
}
