package PrinterSimulation;

public class Event {

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
}
