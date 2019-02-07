package cpu_scheduling;

import myAPI.CircularQueue;
import myAPI.MyAbstractQueue;
import myAPI.MyPriorityQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Scheduling {

    public static void main(String[] args){
        Scheduling s = new Scheduling();
        s.simulator();
    }

    private int cpuTime;
    private MyPriorityQueue<PCB> queue;
    private HashMap<PCB, Integer> roundTime;
    private HashMap<PCB, Integer> waitingTime;
    private HashMap<PCB, Integer> cpu;
    private PCB[] pcbs = new PCB[5];

    public void init(){
        queue = new MyPriorityQueue<>();
        roundTime = new HashMap<>();
        waitingTime = new HashMap<>();
        cpu = new HashMap<>();
        System.out.println("INPUT NAME, NEED_TIME AND PRIORITY:");
        Scanner s = null;
        try {
            s = new Scanner(new File("myFile/process.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++){
            PCB p = createPCB(s.nextLine());
            pcbs[i] = p;
            queue.add(p);
            waitingTime.put(p, 0);
            roundTime.put(p, 0);
            cpu.put(p, 0);
        }
        cpuTime = 0;
    }

    private PCB createPCB(String line){
        String[] info = line.split(" ");
        PCB p = new PCB(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        return p;
    }

    public void simulator(){
        init();
        printInformation();
        while (!queue.isEmpty()){
            cpuTime++;
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

    private void run(PCB p) {
        int needTime = p.getNeedTime()-1;
        int priority = p.getPriority()-1;
        p.setNeedTime(needTime);
        p.setPriority(priority);
        cpu.put(p, cpu.get(p) + 1);
        analyze();
        printInformation();
        if (needTime == 0) p.setState(PCB.FINISH);
        else               p.setState(PCB.READY);
    }

    private void analyze(){
        for (PCB p : pcbs){
            int rt = roundTime.get(p);
            int wt = waitingTime.get(p);
            int ct = cpu.get(p);
            if (p.getState() != PCB.FINISH)
                roundTime.put(p, rt+1);
            if (ct != 0 && p.getState() == PCB.READY)
                cpu.put(p, ct + 1);
            if (p.getState() == PCB.READY)
                waitingTime.put(p, wt + 1);
        }
    }

    private void printReport(){
        System.out.print("name\tWaiting Time\tRoundTime\n");
        for (PCB p : pcbs)
            System.out.printf("%s\t\t\t%d\t\t\t\t%d\n", p.getName(), waitingTime.get(p), roundTime.get(p));
    }

    private void printInformation(){
        System.out.println("CUP_TIME: " + cpuTime);
        System.out.print("name cup_time needTime Priority state\n");
        for (PCB p : pcbs) {
            String state = null;
            if      (p.getState() == 0) state = "Ready";
            else if (p.getState() == 1) state = "Working";
            else if (p.getState() == 2) state = "Finish";
            System.out.printf("%s\t\t%d\t\t%d\t\t%d\t\t%s\n",
                    p.getName(), cpu.get(p), p.getNeedTime(), p.getPriority(), state);
        }
    }
}
