package rail.graph;

public interface GraphIndex<Vertex, Edge> {

    int size();

    int indexOf(String name);

    Vertex getVertex(int index);

    Iterable<Edge> getAdj(int index);
}

