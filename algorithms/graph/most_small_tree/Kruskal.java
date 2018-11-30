package graph.most_small_tree;

import graph.graphs.Edge;
import graph.graphs.EdgeWeightGraph;
import graph.other.UnionFind;
import myAPI.MinHeap;

import java.util.LinkedList;
import java.util.Queue;

public class Kruskal implements MST {

    private Queue<Edge> edges;

    public Kruskal(EdgeWeightGraph g){
        edges = new LinkedList<>();
        mst(g);
    }

    private void mst(EdgeWeightGraph g){
        MinHeap<Edge> pq = new MinHeap<>();
        for(Edge e : g.edges()) pq.add(e);
        UnionFind uf = new UnionFind(g.V());

        while (!pq.isEmpty()){
            Edge e = pq.getMin();
            int v = e.either();
            int w = e.other(v);
            if (uf.isConnected(v, w)) continue;
            edges.add(e);
            uf.union(v, w);
        }
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
