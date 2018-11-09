package PrinterSimulation;

public class Event implements Comparable<Event> {

    private Job job;
    private int arrival_time;

    public Event(Job job, int arrival_time){
        this.job = job;
        this.arrival_time = arrival_time;
    }

    public Job getJob() {
        return job;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    /**
     * 优先级定义
     * pages: 页数越多，优先级越低，只有在累计到一定程度才会影响结果
     * arrival_time: 先到先得，对结果有着主要的影响
     */
    public int getPriority(){
        int a = -3 * arrival_time;
        int b = -1 * job.getNumber_of_pages();
        return a + b;
    }

    @Override
    public int compareTo(Event o) {
        return this.getPriority() - o.getPriority();
    }

    public String toString(){
        return getPriority() + String.format("(%s, %s)", arrival_time, job.getNumber_of_pages());
    }
}
