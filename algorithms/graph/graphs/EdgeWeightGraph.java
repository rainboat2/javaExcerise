package graph.graphs;

import java.util.LinkedList;

public class EdgeWeightGraph {

    private int V;
    private int E;
    private LinkedList<Edge>[] adj;

    public static EdgeWeightGraph getGraph(){
        EdgeWeightGraph g = new EdgeWeightGraph(8);
        int[] v = new int[]{4, 4, 5, 0, 1, 0, 2, 1, 0, 1, 1, 2, 6, 3, 6, 6};
        int[] w = new int[]{5, 7, 7, 7, 5, 4, 3, 7, 2, 2, 3, 7, 2, 6, 0, 4};
        double[] weight = new double[]{0.35, 0.37, 0.28, 0.16, 0.32, 0.38, 0.17, 0.19, 0.26, 0.36, 0.29,
        0.34, 0.40, 0.52, 0.58, 0.93};
        for (int i = 0; i < v.length; i++)
            g.addEdge(new Edge(v[i], w[i], weight[i]));
        return g;
    }

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

    // 该方法的正确运行基于每个边的权值都不相等这一假设！！！！！
    public Iterable<Edge> edges(){
        LinkedList<Edge> edges = new LinkedList<>();
        for (int v = 0; v < V; v++)
            for (Edge e : adj[v])
                if (e.other(v) > v) edges.add(e);
        return edges;
    }
}
