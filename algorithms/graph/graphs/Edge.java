package graph.graphs;

public class Edge implements Comparable<Edge> {

    private int v;
    private int w;
    private double weight;

    public Edge(int v, int w, double weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either(){
        return v;
    }

    public int other(int s){
        if      (s == v) return w;
        else if (s == w) return s;
        else throw new RuntimeException("inconsistent edge");
    }

    public int compareTo(Edge that) {
        if      (this.weight > that.weight)   return 1;
        else if (this.weight == that.weight)  return 0;
        else                                  return -1;
    }

    public String toString(){
        return String.format("%d-%d %.2f", v, w, weight);
    }
}
