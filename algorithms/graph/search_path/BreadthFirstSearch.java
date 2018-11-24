package graph.search_path;

import graph.Graph;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch implements Search {

    private boolean[] marked;
    private int count;

    public BreadthFirstSearch(Graph g, int v){
        marked = new boolean[g.V()];
        bfs(g, v);
    }

    private void bfs(Graph g, int s){
        Queue<Integer> queue = new LinkedList<>();
        marked[s] = true;
        queue.offer(s);
        count++;
        while (!queue.isEmpty()){
            int v = queue.poll();
            for (int w : g.getAdj(v))
                if (!isMarked(w)){
                    marked[w] = true;
                    queue.offer(w);
                    count++;
                }
        }
    }

    public boolean isMarked(int v) {
        return marked[v];
    }

    @Override
    public int count() {
        return count;
    }
}
