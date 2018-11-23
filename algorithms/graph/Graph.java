package graph;

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

    public Iterable<Integer> getAdj(int v){
        return adj[v];
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(V).append(" Vertices").append(E).append(" edge\n");
        for (int v = 0; v < V; v++){
            s.append(v).append(" ");
            for (int w : getAdj(v))
                s.append(w).append(" ");
            s.append("\n");
        }
        return s.toString();
    }

    public static int degree(Graph G, int v){
        int degree = 0;
        for (int w : G.getAdj(v)) degree++;
        return degree;
    }

    public static int maxDegree(Graph G){
        int max = 0;
        for (int v = 0; v < G.V(); v++){
            int degree = degree(G, v);
            if (degree > max)
                max = degree;
        }
    }

    public static double avgDegree(Graph G){
        return 2.0*G.E() / G.V();
    }

    public static int numberOfSelfLoops(Graph G){
        int count = 0;
        for (int v = 0; v < G.V(); v++)
            for (int w : G.getAdj(v))
                if (w == v) count++;
        return count/2;
    }
}
