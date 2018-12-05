package rail;

import java.util.LinkedList;

public class City implements Comparable<City>{

    private String name;
    private int cost;       // 到达该城市的最小花费
    private City shortPath;  // 到达该城市的最短路径的上一个城市
    private LinkedList<Services> outgoing_services;

    public City(String name){
        this.name = name;
        outgoing_services = new LinkedList<>();
    }

    public String getName(){
        return name;
    }

    public int getCost(){ return cost; }

    public void setCost(int cost){ this.cost = cost; }


    public City getShortPath() { return shortPath; }

    public void setShortPath(City shortPath) { this.shortPath = shortPath; }


    public void addServices(Services s){
        outgoing_services.add(s);
    }

    public Iterable<Services> getOutgoing_services(){
        return outgoing_services;
    }

    public Services getServicesTo(String name){
        for (Services s : getOutgoing_services())
            if (s.getDest().getName().equals(name))
                return s;
        return null;
    }

    public int compareTo(City o) { return Integer.compare(cost, o.cost); }

    public String toString(){
        if (shortPath == null) return "null --> " + name;
        else                   return shortPath.name + " --> " + name;
    }
}
