package apply;

import graph.Graph;

public class DepthFirstSearch {

    public static void main(String[] args){
        Graph g = Graph.getGraph();
        DepthFirstSearch d = new DepthFirstSearch(g, 0);
    }

    private boolean[] marked;

    public DepthFirstSearch(Graph g, int s){    // s为搜索的起点
        marked = new boolean[g.V()];
        for (int i = 0; i < marked.length; i++)
            marked[i] = false;
        dfs(g, s);
    }

    private void dfs(Graph graph, int v){
        marked[v] = true;
        visit(v);
        for (int a : graph.getAdj(v))
            if (!isMarked(a)) dfs(graph, a);

    }

    private boolean isMarked(int v){
        return marked[v];
    }

    private void visit(int v){
        System.out.println(v);
    }
}
