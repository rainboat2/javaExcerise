package graph.digraph_search;


public interface ShortPath {

    double distTo(int v);

    boolean hasPathTo(int v);

    Iterable<Integer> pathTo(int v);
}
