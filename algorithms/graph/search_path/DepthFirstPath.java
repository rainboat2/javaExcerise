package graph.search_path;

import graph.graphs.Graph;

import java.util.Stack;

public class DepthFirstPath implements Path {

    public static void main(String[] args){
        DepthFirstPath p = new DepthFirstPath(Graph.getGraph(), 0);
        System.out.println(p.exit_Path_K(Graph.getGraph(), 0, 3, 2));
    }

    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public DepthFirstPath(Graph g, int s){
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        dfs(g, s);
    }

    private void dfs(Graph g, int s){
        marked[s] = true;
        for (int w : g.getAdj(s))
            if (!isMarked(w)){
                edgeTo[w] = s;   //w的父节点为s
                dfs(g, w);
            }
    }

    public boolean isMarked(int v){
        return marked[v];
    }

    /*
     * 节点被访问：
     *  1.有一个父节点
     *  2.为开始节点
     *  按递归的方式进行思考，被访问过的节点必然可达
     */
    public boolean hasPathTo(int v) {
        return isMarked(v);
    }


    @SuppressWarnings("Duplicates")
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> stack = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x])  //不断向父节点移动，直到达到根节点
            stack.push(x);
        stack.push(s);
        return stack;
    }

    public boolean exit_Path_K(Graph g, int i, int j, int k){
        if (i == j && k == 0) return true;
        marked[i] = true;
        for (int w : g.getAdj(i))
            if (!isMarked(w))
                exit_Path_K(g, w, j, k-1);
        if (k == 0) return true;
        else        return false;
    }
}
