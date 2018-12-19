package rail.graph;

import rail.City;
import rail.Services;

import java.io.*;
import java.util.HashMap;

public class Hash_Array_Graph implements GraphIndex<City, Services> {

    private HashMap<String, Integer> map;     // 城市名-城市编号
    private City[] cities;                    // 城市编号-城市对象

    public Hash_Array_Graph(){
        map = new HashMap<>();
        loadGraph();
    }

    public void loadGraph() {
        File file = new File("myFile/services.txt");
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null)
                addCityToGraph(line);

            in = new InputStreamReader(new FileInputStream(file));
            reader = new BufferedReader(in);
            cities = new City[map.size()];
            for (String name : map.keySet())
                cities[map.get(name)] = new City(name);

            while ((line = reader.readLine()) != null)
                addServiceToGraph(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCityToGraph(String line){
        //[from, to, fee, distance]
        String[] info = line.split(" ");
        String from = info[0], to = info[1];

        map.computeIfAbsent(from, k-> map.size());
        map.computeIfAbsent(to, k -> map.size());
    }

    private void addServiceToGraph(String line){
        String[] info = line.split(" ");
        int from = indexOf(info[0]), to = indexOf(info[1]);
        int fee = Integer.parseInt(info[2]), distance = Integer.parseInt(info[3]);

        cities[from].addServices(new Services(cities[to], fee, distance));
    }

    public int size() {
        return map.size();
    }

    public City getVertex(int index) {
        return cities[index];
    }

    public Iterable<Services> getAdj(int index) {
        return cities[index].getOutgoing_services();
    }

    public int indexOf(String name){
        if (map.get(name) == null) return -1;
        else                       return map.get(name);
    }
}
