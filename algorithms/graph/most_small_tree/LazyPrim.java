package graph.most_small_tree;

import graph.graphs.Edge;
import graph.graphs.EdgeWeightGraph;
import myAPI.MinHeap;

import java.util.LinkedList;
import java.util.Queue;

public class LazyPrim implements MST {

    private boolean[] marked;
    private Queue<Edge> edges;
    private MinHeap<Edge> pq;

    public LazyPrim(EdgeWeightGraph g){
        marked = new boolean[g.V()];
        edges = new LinkedList<>();
        pq = new MinHeap<>();
        mst(g);
    }

    private void mst(EdgeWeightGraph g){
        visit(0, g);
        while (!pq.isEmpty()){
            Edge e = pq.getMin();
            int v = e.either();
            int w = e.other(v);
            if (isMarked(v) && isMarked(w)) continue;    //当该边失效了就跳过该边
            edges.add(e);
            if (!isMarked(v)) visit(v, g);
            if (!isMarked(w)) visit(w, g);
        }
    }

    public boolean isMarked(int v){
        return marked[v];
    }

    private void visit(int v, EdgeWeightGraph g){
        marked[v] = true;
        for (Edge e : g.getAdj(v))
            pq.add(e);
    }

    public Iterable<Edge> edges() {
        return edges;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges)
            weight += e.weight();
        return weight;
    }
}
