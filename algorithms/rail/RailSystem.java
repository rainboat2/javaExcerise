package rail;

import rail.graph.GraphIndex;
import rail.graph.GraphName;
import rail.graph.Hash_Array_Graph;
import rail.graph.Hash_Graph;
import rail.shortPath.DijkstraIndex;
import rail.shortPath.DijkstraName;
import rail.shortPath.ShortPath;

import java.util.Scanner;
import java.util.Stack;

public class RailSystem {

    public static void main(String[] args){
        RailSystem r = new RailSystem(5);
        r.run();
    }

    private ShortPath<City> shortPath;

    public RailSystem(int model){
        if (model == 1){
            GraphIndex<City, Services> g = new Hash_Array_Graph();
            this.shortPath = new DijkstraIndex(g);
        }else{
            GraphName<City, Services> g = new Hash_Graph();
            this.shortPath = new DijkstraName(g);
        }
    }

    public void run(){
        Scanner s = new Scanner(System.in);
        while (true){
            try{
                String[] info = getInput(s);
                if (info == null) break;
                String start = info[0], end = info[1];
                Stack<City> path = shortPath.pathTo(start, end);
                System.out.println(recover_route(path));
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("输入有误！请检查输入格式或者查看是否有拼写问题！");
            }
        }
    }

    private String[] getInput(Scanner s){
        System.out.println("输入起点和终点来找到最短路径（输入exit退出）：");
        String line = s.nextLine();
        if (line.equals("exit")) return null;
        return line.split(" ");
    }

    private String recover_route(Stack<City> path){
        if (path == null) return "无可用路径";
        int total_fee = 0, distance = 0;
        StringBuilder builder = new StringBuilder();
        City v = path.pop();
        builder.append(v.getName()).append(" to ");
        while (!path.isEmpty()){
            City w = path.pop();
            Services s = v.getServicesTo(w.getName());
            distance += s.getDistance();
            total_fee += s.getFee();
            //生成路径
            builder.append(w.getName());
            if (!path.isEmpty())
                builder.append(" to ");
            v = w;
        }
        return String.format("total_fee = %d, distance = %d\n", total_fee, distance) + builder.toString();
    }
}
