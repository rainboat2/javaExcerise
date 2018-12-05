package rail;


import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class RailSystem_2 {

    public static void main(String[] args){
        RailSystem_2 r = new RailSystem_2();
        r.run();
    }

    private HashMap<String, City> map;

    public RailSystem_2(){
        map = new HashMap<>();
        loadServices();
    }

    public void run(){
        Scanner s = new Scanner(System.in);
        while (true){
            try {
                System.out.println("输入起点和终点来找到最短路径（输入exit退出）：");
                String line = s.nextLine();
                if (line.equals("exit")) break;
                String[] info = line.split(" ");
                String start = info[0], end = info[1];
                cal(start, end);
                System.out.println(totalCost(end));
                System.out.println(recover_route(end));
            }catch (Exception e){
                System.out.println("错误输入，请检查输入格式或城市名称！");
            }
        }
        s.close();
    }

    public String totalCost(String end){
        Stack<City> path = pathTo(end);
        int total_fee = 0, distance = 0;
        City v = path.pop();
        while (!path.isEmpty()){
            City w = path.pop();
            Services s = v.getServicesTo(w.getName());
            total_fee += s.getFee();
            distance += s.getDistance();
            v = w;
        }
        return String.format("total_fee = %d, distance = %d", total_fee, distance);
    }

    public String recover_route(String end){
        Stack<City> path = pathTo(end);
        StringBuilder builder = new StringBuilder();
        while (!path.isEmpty()){
            builder.append(path.pop().getName());
            if (!path.isEmpty())
                builder.append(" to ");
        }
        return builder.toString();
    }

    public Stack<City> pathTo(String end){
        Stack<City> s = new Stack<>();
        City c = map.get(end);
        while (c != map.get(c.getShortPath().getName())){
            s.push(c);
            c = map.get(c.getShortPath().getName());
        }
        s.add(c);
        return s;
    }

    public void loadServices(){
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

        map.computeIfAbsent(from, k->new City(from));
        map.computeIfAbsent(to, k->new City(to));
        map.get(from).addServices(new Services(map.get(to), fee, distance));
    }

    public void reset(){
        for (City c : map.values()) {
            c.setCost(Integer.MAX_VALUE);
            c.setShortPath(null);
        }
    }

    /*
     * Dijkstra 算法
     * 基本思路：
     * 1. 初始化开始节点，加入到优先队列（costHeap，花费越小，优先级越高）
     * 2. 选出优先级最高的节点，并对该节点进行relax（放松）操作
     * 3. 到达终点后停止
     */
    public void cal(String start, String dest){
        City startCity = map.get(start);
        CostHeap cost = new CostHeap();
        reset();
        startCity.setShortPath(startCity);
        startCity.setCost(0);
        cost.add(startCity, startCity.getCost());

        while (!cost.isEmpty()){
            City c = cost.getMin();
            relax(c, cost);
            if (c.getName().equals(dest)) break;   //对c进行放松操作后说明c已经找到，无需继续寻找下去
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
