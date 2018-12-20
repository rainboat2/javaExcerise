package cpu_scheduling;

import myAPI.MyPriorityQueue;

import java.util.HashMap;
import java.util.Set;

public class PriorityScheduling {

    public static void main(String[] args){
        PriorityScheduling c = new PriorityScheduling();
        c.simulator();
    }

    private MyPriorityQueue<PCB> queue;
    private HashMap<PCB, Integer> roundTime;
    private HashMap<PCB, Integer> waitingTime;

    public void init(){
        queue = new MyPriorityQueue<>();
        roundTime = new HashMap<>();
        waitingTime = new HashMap<>();
        for (int i = 1; i <= 5; i++){
            PCB p = new PCB("p" + i);
            queue.add(p);
            waitingTime.put(p, 0);
            roundTime.put(p, 0);
        }
    }

    public void simulator(){
        init();
        while (!queue.isEmpty()){
            PCB process = queue.poll();
            process.setState(PCB.WORKING);
            run(process);
            if (process.getState() != PCB.FINISH)
                queue.add(process);
        }
        end();
    }

    private void end() {
        printReport();
    }

    private void run(PCB process) {
        int needTime = process.getNeedTime()-1;
        int priority = process.getPriority()-1;
        process.setNeedTime(needTime);
        process.setPriority(priority);
        analyze();
        if (needTime == 0) process.setState(PCB.FINISH);
        else               process.setState(PCB.READY);
    }

    private void analyze(){
        Set<PCB> set = roundTime.keySet();
        for (PCB p : set){
            int rt = roundTime.get(p);
            int wt = waitingTime.get(p);
            if (p.getState() != PCB.FINISH)
                roundTime.put(p, rt+1);
            if (p.getState() == PCB.READY)
                waitingTime.put(p, wt+1);
        }
    }

    private void printReport(){
        System.out.print("name\tWaiting Time\tRoundTime\n");
        for (PCB p : waitingTime.keySet()){
            System.out.printf("%s\t\t\t%d\t\t\t\t%d\n", p.getName(), waitingTime.get(p), roundTime.get(p));
        }
    }
}
