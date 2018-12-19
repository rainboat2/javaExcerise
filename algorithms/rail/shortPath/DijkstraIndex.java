package rail.shortPath;

import rail.City;
import rail.Services;
import rail.graph.GraphIndex;

import java.util.Stack;

public class DijkstraIndex implements ShortPath<City> {

    private GraphIndex<City, Services> g;

    public DijkstraIndex(GraphIndex<City, Services> g){
        this.g = g;
    }

    public Stack<City> pathTo(String start, String end) {
        int from = g.indexOf(start), to = g.indexOf(end);
        int[] pathTo = cal_route(from, to);
        Stack<City> stack = new Stack<>();
        int i;
        for (i = to; i != pathTo[i]; i = pathTo[i])
            stack.add(g.getVertex(i));
        stack.add(g.getVertex(i));
        return stack;
    }

    @SuppressWarnings("Duplicates")
    public int[] cal_route(int from, int dest){
        int N = g.size();
        int[] cost = new int[N];
        int[] pathTo = new int[N];
        boolean[] marked = new boolean[N];
        for (int i = 0; i < N; i++){
            cost[i] = Integer.MAX_VALUE;
            pathTo[i] = from;
            marked[i] = false;
        }
        cost[from] = 0;
        for (int i = 0; i < N; i++){
            int v = getMin(cost, marked);
            if (v == dest) break;     //已经找到到达dest的最短路径，不用再继续寻找下去
            relax(v, cost, pathTo);
            marked[v] = true;
        }
        return pathTo;
    }


    private int getMin(int[] cost, boolean[] marked){
        int min = 0;
        while (min < cost.length && marked[min]) min++;
        for (int i = min; i < cost.length; i++)
            if (!marked[i] && cost[min] > cost[i])
                min = i;
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
