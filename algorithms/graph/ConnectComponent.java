package graph;

import graph.graphs.Graph;

public class ConnectComponent {

    int count;
    boolean[] marked;
    int[] id;

    public ConnectComponent(Graph g){
        id = new int[g.V()];
        for (int i = 0; i < g.V(); i++)
            if (!isMarked(i)){
                dfs(g, i);
                count++;
            }
    }

    public void dfs(Graph g, int v){
        marked[v] = true;
        id[v] = count;
        for (int w : g.getAdj(v))
            if (!isMarked(w)) dfs(g, w);
    }

    public boolean isMarked(int v){
        return marked[v];
    }

    public boolean isConnected(int v, int w){
        if (v >= id.length || w >= id.length)
            return false;
        return id[v] == id[w];
    }

    public int id(int v){
        return id[v];
    }
}
