package graph.digraph_search;

import graph.graphs.DirectedEdge;
import graph.graphs.EdgeWeightedDigraph;

import java.util.Stack;

public class Dijkstra implements ShortPath {

    public static void main(String[] args){
        EdgeWeightedDigraph g = EdgeWeightedDigraph.getGraph();
        Dijkstra d = new Dijkstra(g, 0);
        for (int i = 0; i < g.V(); i++) {
            for (int j : d.pathTo(i))
                System.out.print(j + " ");
            System.out.println();
        }
    }

    private double[] distTo;
    private int[] edgeTo;
    private boolean[] marked;   // 标记该节点是否在最短路径树中

    public Dijkstra(EdgeWeightedDigraph g, int s){
        distTo = new double[g.V()];
        edgeTo = new int[g.V()];
        marked = new boolean[g.V()];
        for (int i = 0; i < g.V(); i++){
            distTo[i] = Double.POSITIVE_INFINITY;
            edgeTo[i] = s;
        }
        distTo[s] = 0.0;
        for (int i = 0; i < g.V(); i++) {
            int v = getMinDistVertex();
            relax(g, v);
            marked[v] = true;
        }
    }


    private int getMinDistVertex(){
        int min = 0;
        while (min< marked.length && marked[min]) { min++; }  //找到不在最短路径树中的第一个节点

        for (int i = min; i < distTo.length; i++)
            if (!marked[i] && (distTo[min] > distTo[i]))
                min = i;
        return min;
    }

    private void relax(EdgeWeightedDigraph g, int v){
        for (DirectedEdge e : g.getAdj(v)){
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()){
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = v;
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> s = new Stack<>();
        while (v != edgeTo[v]){
            s.push(v);
            v = edgeTo[v];
        }
        return s;
    }
}
