package rail.shortPath;

import rail.City;
import rail.Services;
import rail.graph.GraphIndex;

import java.util.Stack;

public class DijkstraIndex implements ShortPath<City> {

    private GraphIndex<City, Services> g;
    private int[] pathTo;
    private int[] cost;

    public DijkstraIndex(GraphIndex<City, Services> g){
        this.g = g;
    }

    public Stack<City> pathTo(String start, String end) {
        int from = g.indexOf(start), to = g.indexOf(end);
        cal_route(from, to);
        if (cost[to] == Integer.MAX_VALUE) return null;  //end不可达，无路径可以返回
        Stack<City> stack = new Stack<>();
        int i;
        for (i = to; i != pathTo[i]; i = pathTo[i])
            stack.add(g.getVertex(i));
        stack.add(g.getVertex(i));
        return stack;
    }

    public void cal_route(int from, int dest){
        int N = g.size();
        cost = new int[N];
        pathTo = new int[N];
        boolean[] marked = new boolean[N];
        for (int i = 0; i < N; i++){
            cost[i] = Integer.MAX_VALUE;
            pathTo[i] = from;
            marked[i] = false;
        }
        cost[from] = 0;
        for (int i = 0; i < N; i++){
            int v = getMin(marked);
            if (v == -1)   break;     //已经找不到下一个可达的节点，算法结束
            if (v == dest) break;     //已经找到到达dest的最短路径，不用再继续寻找下去
            relax(v, cost, pathTo);
            marked[v] = true;
        }
    }


    private int getMin(boolean[] marked){
        int min = 0;
        while (min < cost.length && marked[min]) min++;
        for (int i = min; i < cost.length; i++)
            if (!marked[i] && cost[min] > cost[i])
                min = i;
        if (cost[min] == Integer.MAX_VALUE) min = -1;
        return min;
    }


    private void relax(int v, int[] cost, int[] pathTo){
        for (Services s : g.getAdj(v)){
            int w = g.indexOf(s.getDest().getName());
            if (cost[w] > cost[v] + s.getFee()){
                cost[w] = cost[v] + s.getFee();
                pathTo[w] = v;
            }
        }
    }
}
