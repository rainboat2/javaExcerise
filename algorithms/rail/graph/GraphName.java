package rail.graph;

public interface GraphName<Vertex, Edge> {

    int size();

    Vertex getVertex(String name);

    Iterable<Edge> getAdj(String name);

    Iterable<Vertex> getVertexes();
}
