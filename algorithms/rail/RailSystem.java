package rail;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class RailSystem {

    public static void main(String[] args){
        RailSystem r = new RailSystem();
        r.run();
    }

    private HashMap<String, Integer> map;     // 城市名-城市编号
    private City[] cities;                    // 城市编号-城市对象

    public RailSystem(){
        map = new HashMap<>();
        loadServices();
    }

    public void run(){
        Scanner s = new Scanner(System.in);
        while (true){
            try {
                System.out.println("输入起点和终点（输入exit退出）：");
                String line = s.nextLine();
                if (line.equals("exit")) break;
                String[] info = line.split(" ");
                int begin = indexOf(info[0]), dest = indexOf(info[1]);
                int[] pathTo = cal_route(begin);

                Stack<Integer> stack = new Stack<>();
                int v;
                for (v = dest; v != pathTo[v]; v = pathTo[v])
                    stack.add(v);
                int total_fee = 0, distance = 0;
                while (!stack.isEmpty()){
                    int w = stack.pop();
                    Services se = cities[v].getServicesTo(cities[w].getName());
                    total_fee += se.getFee();
                    distance += se.getDistance();
                    v = w;
                }
                System.out.printf("total_fee = %s, distance = %s\n", total_fee, distance);
                System.out.println(recover_route(dest, pathTo));
            }catch (Exception e){
                System.out.println("输入有误，请重新输入！");
            }
        }
        s.close();
    }

    public void loadServices(){
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

    public int[] cal_route(int s){
        int N = size();
        int[] cost = new int[N];
        int[] pathTo = new int[N];
        boolean[] marked = new boolean[N];
        for (int i = 0; i < N; i++){
            cost[i] = Integer.MAX_VALUE;
            pathTo[i] = s;
            marked[i] = false;
        }
        cost[s] = 0;
        for (int i = 0; i < N; i++){
            int v = getMin(cost, marked);
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
        for (Services s : getServices(v)){
            int w = indexOf(s.getDest().getName());
            if (cost[w] > cost[v] + s.getFee()){
                cost[w] = cost[v] + s.getFee();
                pathTo[w] = v;
            }
        }
    }

    public String recover_route(int dest, int[] pathTo){
        Stack<Integer> stack = new Stack<>();
        int v;
        for (v = dest; v != pathTo[v]; v = pathTo[v])
            stack.add(v);
        stack.add(v);
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()){
            int index = stack.pop();
            if   (stack.isEmpty()) builder.append(getCityName(index));
            else                   builder.append(getCityName(index)).append(" to ");
        }
        builder.append("\n");
        return builder.toString();
    }


    public String  getCityName(int index){
        return cities[index].getName();
    }

    public int indexOf(String name){
        if (map.get(name) == null) return -1;
        else                       return map.get(name);
    }

    public int size() {
        return map.size();
    }

    public Iterable<Services> getServices(int index){
        return cities[index].getOutgoing_services();
    }


}
