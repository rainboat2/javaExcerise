package rail.shortPath;

import rail.City;
import rail.myAPI.CostHeap;
import rail.Services;
import rail.graph.GraphName;

import java.util.Stack;

public class DijkstraName implements ShortPath<City> {

    private GraphName<City, Services> g;

    public DijkstraName(GraphName<City, Services> g){
        this.g = g;
    }

    public Stack<City> pathTo(String start, String end) {
        cal_route(start, end);
        return pathTo(end);
    }

    public Stack<City> pathTo(String end){
        Stack<City> s = new Stack<>();
        City c = g.getVertex(end);
        while (c != g.getVertex(c.getShortPath().getName())){
            s.push(c);
            c = g.getVertex(c.getShortPath().getName());
        }
        s.add(c);
        return s;
    }

    public void reset(){
        for (City c : g.getVertexes()){
            c.setShortPath(null);
            c.setCost(Integer.MAX_VALUE);
        }
    }

    /*
     * DijkstraIndex 算法
     * 基本思路：
     * 1. 初始化开始节点，加入到优先队列（costHeap，花费越小，优先级越高）
     * 2. 选出优先级最高的节点，并对该节点进行relax（放松）操作
     * 3. 到达终点后停止
     */
    public void cal_route(String start, String dest){
        City startCity = g.getVertex(start);
        CostHeap cost = new CostHeap();
        reset();
        startCity.setShortPath(startCity);
        startCity.setCost(0);
        cost.add(startCity, startCity.getCost());

        while (!cost.isEmpty()){
            City c = cost.getMin();
            if (c.getName().equals(dest)) break;   //对c进行放松操作后说明c已经找到，无需继续寻找下去
            relax(c, cost);
        }
    }

    /*
     * “放松”操作
     * 假设两条路径，(v--w为图中的一条边）
     *      1.s-->……-->w,
     *      2.s-->……-->v-->w
     * 若路径2花费小于路径1，则将w的前驱节点设为v
     *
     * 该操作为最短路径算法的关键操作
     */
    public void relax(City c, CostHeap cost){
        for (Services s : c.getOutgoing_services()){
            City dest = s.getDest();
            if (dest.getCost() > c.getCost() + s.getFee()){ //直接到达dest的花费 > 先到c，再到dest的花费
                dest.setCost(c.getCost() + s.getFee());
                dest.setShortPath(c);
                if (cost.contains(dest))  cost.change(dest, dest.getCost());
                else                      cost.add(dest, dest.getCost());
            }
        }
    }
}
