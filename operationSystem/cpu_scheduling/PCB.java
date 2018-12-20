package cpu_scheduling;

public class PCB implements Comparable<PCB>{

    public static final int READY = 0;
    public static final int WORKING = 1;
    public static final int FINISH = 2;

    private String name;
    private int needTime;
    private int priority;
    private int state;

    public PCB(String name){
        this.name = name;
        this.needTime = randomNumber();
        this.priority = randomNumber();
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

    private int randomNumber(){
        return (int)(Math.random()*8 + 1);
    }

    public int compareTo(PCB o) {
        return Integer.compare(this.priority, o.priority);
    }
}
