package PrinterSimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Fifo extends Simulator{

    private int progress_bar;
    private int current_time;
    private Event current_event;
    private MyQueue<Event> toDOList;
    private ArrayList<Integer> waitTimes;
    private ArrayList<String>  result;

    private void init(){
        this.progress_bar = 0;
        this.current_time = 0;
        this.current_event = null;
        this.toDOList = new MyQueue<>();
        this.waitTimes = new ArrayList<>();
        this.result = new ArrayList<>();
        result.add("FIFO Simulator\n");
        super.workLoad.clear();
    }

    /**
     * 每秒钟需要做的事情
     * 1. 处理请求(维护一个进度条，表示当前任务处理进度，处理结束则弹出进行下一个）
     * 2. 检查是否有新的请求,若有，则添加到队列中
     *
     */
    public void simulator(String workPath){
        init();
        loadWork(workPath);
        while(!workLoad.isEmpty() && current_event != null){
            current_time++;
            solveRequest();
            checkArrival();
        }
        result.add(latency());
        outputResult();
    }

    private void solveRequest(){
        if       (!hasJob())       tryToGetNextWork();
        else if  (isFinished())    moveToNextWork();
        else                       doJob();
    }

    private void checkArrival(){
        if (workLoad.peek().getArrival_time() != current_time) return;
        Event event = workLoad.deQueue();
        result.add(String.format("/tArrival: %s pages come from %s at %s seconds",
                event.getJob().getNumber_of_pages(), event.getJob().getUser(), event.getArrival_time()));
        toDOList.enQueue(event);
    }

    private void    doJob() { progress_bar++;}
    private boolean hasJob(){ return current_event != null;}
    private boolean isFinished(){
        int total_process = current_event.getJob().getNumber_of_pages() * seconds_per_page;
        return progress_bar == total_process;
    }

    private void moveToNextWork(){
        endCurrentWork();
        tryToGetNextWork();
    }

    private void endCurrentWork(){
        this.progress_bar = 0;
        waitTimes.add(countWaitTime(current_event));
        current_event = null;
    }

    private void tryToGetNextWork(){
        if (toDOList.isEmpty()) return;
        current_event = toDOList.deQueue();
        result.add(
                String.format("Servicing: %d pages from %s at %d seconds",
                current_event.getJob().getNumber_of_pages(),
                current_event.getJob().getUser(),
                current_event.getArrival_time()));
    }

    /*
     关于如何计算等待时间：
        每个Event带有arrive_time和页数, 可计算solve_time = pages * seconds_per_page
        wait_time = end_time - arrive_time - solve_time
     */
    private int countWaitTime(Event event){
        int solve_time = event.getJob().getNumber_of_pages() * seconds_per_page;
        int wait_time = this.current_time - event.getArrival_time() - solve_time;
        return wait_time;
    }

    private String latency(){
        int total_jobs = waitTimes.size();
        int aggregate_latency = 0;
        for (int time : waitTimes)  aggregate_latency += time;
        int mean_latency = aggregate_latency / total_jobs;
        return String.format("\ttotal_jobs: %d\n\tAggregate latency:%d\n\tMean latency:%d",
                total_jobs, aggregate_latency, mean_latency);
    }

    private void outputResult(){
        File file = new File("result.out");
        try {
            PrintWriter out = new PrintWriter(file);
            for (String line : result)
                out.println(line);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Test{

    public static void main(String[] args){
        Fifo fifo = new Fifo();
        fifo.simulator("myFile/Experiment 1/arbitrary.out");
    }
}
