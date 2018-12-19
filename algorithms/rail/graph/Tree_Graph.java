package rail.graph;

import rail.City;
import rail.Services;
import rail.myAPI.BinarySearchTree;

import java.io.*;

public class Tree_Graph implements GraphName<City, Services>, GraphIndex<City, Services> {

    BinarySearchTree<String, City> tree;

    public Tree_Graph(){
        tree = new BinarySearchTree<>();
        loadGraph();
    }

    @SuppressWarnings("Duplicates")
    public void loadGraph(){
        File file = new File("myFile/services.txt");
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null)
                addToGraph(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToGraph(String line){
        String[] info = line.split(" ");
        String from = info[0], to = info[1];
        int fee = Integer.parseInt(info[2]), distance = Integer.parseInt(info[3]);

        if (tree.get(from) == null) tree.put(from, new City(from));
        if (tree.get(to) == null) tree.put(to,     new City(to));
        tree.get(from).addServices(new Services(tree.get(to), fee, distance));
    }


    public int indexOf(String name) {
        return tree.rank(name);
    }

    public City getVertex(int index) {
        String name = tree.select(index);
        return tree.get(name);
    }

    public Iterable<Services> getAdj(int index) {
        return getVertex(index).getOutgoing_services();
    }

    public int size() {
        return tree.size();
    }

    public City getVertex(String name) {
        return tree.get(name);
    }

    public Iterable<Services> getAdj(String name) {
        return getVertex(name).getOutgoing_services();
    }

    public Iterable<City> getVertexes() {
        return this.tree;
    }
}
