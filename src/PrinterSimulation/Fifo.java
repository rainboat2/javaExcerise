package PrinterSimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Fifo extends Simulator{

    private int progress_bar;
    private int current_time;
    private MyQueue<Event> works;
    private ArrayList<Integer> waitTimes;
    private ArrayList<String>  result;

    private void init(){
        this.progress_bar = 0;
        this.current_time = 0;
        this.works = new MyQueue<>();
        this.waitTimes = new ArrayList<>();
        this.result = new ArrayList<>();
        result.add("FIFO Simulator");
        super.workLoad.clear();
    }

    /**
     * 每秒钟需要做的事情
     * 1. 处理当前请求(维护一个进度条，表示队列最顶上元素的处理进度，处理结束则弹出进行下一个）
     * 2. 检查是否有新的请求,若有，则添加到队列中
     *
     * 关于如何计算等待时间：
     * 每个Event带有arrive_time和页数, 可计算solve_time = pages * seconds_per_page
     * wait_time = end_time - arrive_time - solve_time
     */
    public void simulator(String workPath){
        init();
        loadWork(workPath);
        while(!workLoad.isEmpty()){
            current_time++;
            if (hasJob()){
                doJob();
                if (isFinished()) moveToNextWork();
            }
            checkArrival();
        }
        result.add(latency());
        outputResult();
    }

    private void    doJob() { progress_bar++;}
    private boolean hasJob(){ return !works.isEmpty();}
    private boolean isFinished(){
        Event current_event = works.peek();
        int total_process = current_event.getJob().getNumber_of_pages() * seconds_per_page;
        return progress_bar == total_process;
    }

    private void moveToNextWork(){
        this.progress_bar = 0;
        Event current_event = works.deQueue();
        waitTimes.add(countWaitTime(current_event));
        result.add("/tArriving: %d pages from %s ")
    }

    private int countWaitTime(Event event){
        int solve_time = event.getJob().getNumber_of_pages() * seconds_per_page;
        int wait_time = this.current_time - event.getArrival_time() - solve_time;
        return wait_time;
    }

    private void checkArrival(){
        if (workLoad.peek().getArrival_time() != current_time) return;
        Event event = workLoad.deQueue();
        result.add(String.format("/tArrival: %s pages come from %s at %s seconds",
                event.getJob().getNumber_of_pages(), event.getJob().getUser(), event.getArrival_time()));
        works.enQueue(event);
    }

    private String latency(){
        int total_jobs = waitTimes.size();
        int aggregate_latency = 0;
        for (int time : waitTimes)  aggregate_latency += time;
        int mean_latency = aggregate_latency / total_jobs;
        return String.format("\ttotal_jobs: %d\n\tAggregate latency:%d\n\tMean latency:%d",
                total_jobs, aggregate_latency, mean_latency);
    }

    private String outputResult(){
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
