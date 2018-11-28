package graph.search_path;

import graph.graphs.Graph;

public class DepthFirstSearch implements Search{

    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph g, int s){
        marked = new boolean[g.V()];
        dfs(g, s);
    }

    public void dfs(Graph g, int s){
        marked[s] = true;
        count++;
        for (int v : g.getAdj(s))
            if (!isMarked(v)) dfs(g, v);
    }

    public boolean isMarked(int V){
        return marked[V];
    }

    public int count(){
        return count;
    }
}
