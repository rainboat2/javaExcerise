package graph.search_path;

import graph.Graph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstPath implements Path {

    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public BreadthFirstPath(Graph g, int s){
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        bfs(g, s);
    }

    private void bfs(Graph g, int v){
        Queue<Integer> queue = new LinkedList<>();
        marked[v] = true;
        queue.offer(v);
        while (!queue.isEmpty()){
            v = queue.poll();
            for (int w : g.getAdj(v))
                if (!isMarked(w)){
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.offer(w);
                }
        }
    }

    private boolean isMarked(int v){
        return marked[v];
    }

    public boolean hasPathTo(int v) {
        return isMarked(v);
    }


    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> stack = new Stack<>();
        for (int x = v; x != s; x++)
            stack.push(x);
        return stack;
    }
}
