package graph.other;

import graph.graphs.Graph;

public class Cycle {

    private static final int WHITE = 0;
    private static final int GREY = 1;
    private static final int BLACK = 2;

    private Graph g;
    private int[] mark;
    private int[] parent;

    public void Cycle(){
        setGraph();
    }

    private void setGraph(){
        g = new Graph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
    }

    private void cycle(int v){
        mark = new int[g.V()];
        parent = new int[g.V()];
        for (int i = 0; i < g.V(); i++){
            mark[i] = WHITE;
            parent[i] = BLACK;
        }
        dfs(v);
    }

    private void dfs(int v){
        if (mark[v] == GREY) showPath(v);  //为灰色的时候说明曾经访问过，但是又回到这里，一定有环
        if (mark[v] == BLACK) return;
        for (int w : g.getAdj(v)){
            parent[w] = v;
            dfs(w);
        }
        mark[v] = BLACK;
    }

    //TODO：如何将环打印出来，
    private void showPath(int v){

    }
}
