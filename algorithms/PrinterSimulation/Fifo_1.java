package PrinterSimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.AbstractQueue;
import java.util.LinkedList;

public class Fifo_1 extends Simulator{

    private int progress_bar;
    private int current_time;
    private Event current_event;
    private AbstractQueue<Event> workflow;
    private LinkedList<Integer> latencyTimes;
    private LinkedList<String> logs;

    private void init(){
        this.progress_bar = 0;
        this.current_time = 0;
        this.current_event = null;
        this.workflow = new MyPriorityQueue<>();
        this.latencyTimes = new LinkedList<>();
        this.logs = new LinkedList<>();
        logs.add("FIFO Simulation \n");
        super.workLoad.clear();
    }

    public void simulator(String workPath, String outPath){
        init();
        loadWork(workPath);
        while(!workLoad.isEmpty() || current_event != null){
            current_time++;
            checkArrival();
            solveRequest();
        }
        logs.add(analyzeLatencyTimes());
        outputReport(outPath);
    }

    private void solveRequest(){
        // 查看是否需要执行添加任务的操作
        if       (!hasJob())       tryToGetWork();
        else if  (isFinished())    moveToNextWork();
        // 执行任务
        if (hasJob())  doJob();
    }

    private void checkArrival(){
        // 当同时有多个请求到来的时候通过循环获得所有的请求
        while (!workLoad.isEmpty() && workLoad.peek().getArrival_time() == current_time){
            Event event = workLoad.poll();
            logs.add(String.format("\tArrival: %s pages from %s at %s seconds",
                    event.getJob().getNumber_of_pages(), event.getJob().getUser(), event.getArrival_time()));
            workflow.add(event);
        }
    }

    private void    doJob() { progress_bar++;}
    private boolean hasJob(){ return current_event != null;}
    private boolean isFinished(){
        int total_process = current_event.getJob().getNumber_of_pages() * seconds_per_page;
        return progress_bar == total_process;
    }

    private void moveToNextWork(){
        endCurrentWork();
        tryToGetWork();
    }

    private void endCurrentWork(){
        this.progress_bar = 0;
        latencyTimes.add(countLatencyTime(current_event));
        current_event = null;
    }

    private void tryToGetWork(){
        if (workflow.isEmpty()) return;
        current_event = workflow.poll();
        logs.add(
                String.format("\tServicing: %d pages from %s at %d seconds",
                current_event.getJob().getNumber_of_pages(),
                current_event.getJob().getUser(),
                current_time));
    }

    private int countLatencyTime(Event event){
        int solve_time = event.getJob().getNumber_of_pages() * seconds_per_page;
        int latency_time = this.current_time - event.getArrival_time() - solve_time;
        return latency_time;
    }

    private String analyzeLatencyTimes(){
        int total_jobs = latencyTimes.size();
        int aggregate_latency = 0;
        for (int time : latencyTimes)  aggregate_latency += time;
        double mean_latency = (double)aggregate_latency / (double)total_jobs;

        return String.format("\n\tTotal_jobs: %d" +
                             "\n\tAggregate latency:%d seconds" +
                             "\n\tMean latency:%.3f seconds",
                total_jobs, aggregate_latency, mean_latency);
    }

    private void outputReport(String outPath){
        File file = new File(outPath);
        try {
            PrintWriter out = new PrintWriter(file);
            for (String line : logs) // logs为记录打印输出的一个数组
                out.println(line);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

