package rail.graph;

import rail.City;
import rail.Services;

import java.io.*;
import java.util.HashMap;

public class Hash_Graph implements GraphName<City, Services>{

    HashMap<String, City> map;

    public Hash_Graph(){
        map = new HashMap<>();
        loadGraph();
    }

    public void loadGraph(){
        File file = new File("myFile/services.txt");
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null)
                addToGraph(line);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToGraph(String line){
        String[] info = line.split(" ");
        String from = info[0], to = info[1];
        int fee = Integer.parseInt(info[2]), distance = Integer.parseInt(info[3]);

        map.computeIfAbsent(from, k->new City(from));
        map.computeIfAbsent(to, k->new City(to));
        map.get(from).addServices(new Services(map.get(to), fee, distance));
    }

    public int size() {
        return map.size();
    }

    public City getVertex(String name) {
        return map.get(name);
    }

    public Iterable<Services> getAdj(String name) {
        return map.get(name).getOutgoing_services();
    }

    public Iterable<City> getVertexes() {
        return map.values();
    }
}
