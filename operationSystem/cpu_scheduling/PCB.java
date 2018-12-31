package cpu_scheduling;

import java.util.Random;

public class PCB implements Comparable<PCB>{

    public static final int READY = 0;
    public static final int WORKING = 1;
    public static final int FINISH = 2;

    private String name;
    private int needTime;
    private int priority;
    private int state;

    public PCB(String name, int needTime, int priority){
        this.name = name;
        this.needTime = needTime;
        this.priority = priority;
        this.state = READY;
    }

    public String getName() {
        return name;
    }

    public int getNeedTime() {
        return needTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int compareTo(PCB o) {
        return Integer.compare(this.priority, o.priority);
    }
}
