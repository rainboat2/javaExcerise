package exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class GraphClass<Type> {

    public static void main(String[] args){
        GraphClass<String> g = GraphClass.getGraph();
        g.depthFirst("V1");
    }

    private ArrayList<Vertex> vertices;
    int edgeNumber;

    private class Vertex{
        Type value;
        LinkedList<EdgeNode> adj;

        @SuppressWarnings("unchecked")
        public Vertex(Type value){
            this.value = value;
            adj = (LinkedList<EdgeNode>) new LinkedList();
        }
    }

    private class EdgeNode{
        int index;
        int weight;

        public EdgeNode(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }
    }

    @SuppressWarnings("unchecked")
    public GraphClass(){
        vertices = (ArrayList<Vertex>) new ArrayList();
        edgeNumber = 0;
    }

    public int vertexNumber(){ return vertices.size();}

    public int indexOf(Type a){
        for (int i = 0; i < vertexNumber(); i++)
            if (vertices.get(i).value.equals(a))
                return i;
        return -1;
    }

    public void addEdge(Type a, Type b){
        addEdge(a, b, 0);
    }

    public void addEdge(Type a, Type b, int weight){
        int i = indexOf(a);
        int j = indexOf(b);
        if (i != -1 && j != -1){
            vertices.get(i).adj.add(new EdgeNode(i, weight));
            vertices.get(i).adj.add(new EdgeNode(j, weight));
            edgeNumber++;
        }
    }

    public void addVertex(Type a){
        vertices.add(new Vertex(a));
    }

    public void addAllVertexes(Collection<Type> collection){
        for (Type a : collection)
            addVertex(a);
    }

    public void depthFirst(Type begin){
        depthFirst(indexOf(begin));
    }

    public void depthFirst(int begin){
        if (begin < 0 || begin >= vertexNumber())
            throw new IndexOutOfBoundsException();
        boolean[] marked = new boolean[vertexNumber()];
        depthFirst(begin, marked);
    }

    private void depthFirst(int v, boolean[] marked){
        marked[v] = true;
        visit(v);
        for (EdgeNode adj : vertices.get(v).adj)
            if (! marked[adj.index]) depthFirst(adj.index, marked);
    }

    private void visit(int v){
        System.out.println(vertices.get(v).value);
    }

    public static GraphClass<String> getGraph(){
        String[] vertices = new String[]{"V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7"};
        GraphClass<String> g = new GraphClass<>();
        g.addAllVertexes(Arrays.asList(vertices));
        String[][] edge = new String[][]{{"V0", "V1"}, {"V0", "V2"}, {"V1", "V3"}, {"V1", "V4"},
                {"V3", "V7"}, {"V4", "V7"}, {"V2", "V5"}, {"V2", "V6"}, {"V5", "V6"}};
        for (String[] anEdge : edge) g.addEdge(anEdge[0], anEdge[1]);
        return g;
    }
}
