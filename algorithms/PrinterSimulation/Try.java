package PrinterSimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.AbstractQueue;
import java.util.LinkedList;

class TryTest{
    public static void main(String[] args){
        Try t = new Try();
        t.simulator("myFile/Experiment 1/arbitrary.run", "myFile/Experiment 1/arbitrary.myOut", 2);
        t.simulator("myFile/Experiment 1/bigfirst.run", "myFile/Experiment 1/bigfirst.myOut", "Priority", 3);
    }
}

public class Try extends Simulator{

    private int current_time;
    private Printer[] printers;
    private PrintWriter log_printer;

    public void simulator(String workPath, String outPath){
        simulator(workPath, outPath, "FIFO");
    }

    public void simulator(String workPath, String outPath, String pattern){
        simulator(workPath, outPath, pattern, 1);
    }

    public void simulator(String workPath, String outPath, int printerNumbers){
        simulator(workPath, outPath, "FIFO", printerNumbers);
    }

    public void simulator(String workPath, String outPath, String pattern, int printerNumbers){
        init(workPath, outPath, pattern, printerNumbers);
        if (pattern.equals("Priority"))  log_printer.println("Priority Simulator\n");
        else                             log_printer.println("FIFO Simulator\n");
        while (!allWorkDone()){
            addCurrentTime();
            checkArrival();
            runAllPrinters();
        }
        String report = analyzeLatencyTimes();
        log_printer.println(report);
        end();
    }

    private void runAllPrinters(){
        for (Printer printer : printers)
            printer.work();
    }

    private void init(String workPath, String outPath, String pattern, int printerNumbers){
        // 重新开始记时
        current_time = 0;
        printers = new Printer[printerNumbers];
        createPrinter(outPath);
        for (int i = 0; i < printerNumbers; i++) printers[i] = new Printer(pattern, log_printer);
        super.workLoad.clear();
        loadWork(workPath);
    }

    private void end(){
        log_printer.close();
    }

    private void createPrinter(String outPath){
        try{
            log_printer = new PrintWriter(new File(outPath));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public int getCurrentTime() { return current_time;}

    private void addCurrentTime() { current_time++;}

    private boolean allWorkDone(){
        boolean flag = workLoad.isEmpty();
        for (Printer printer : printers)
            flag = flag && printer.isFree();
        return flag;
    }

    private void checkArrival(){
        // 当同时有多个请求到来的时候通过循环获得所有的请求
        while (!workLoad.isEmpty() && workLoad.peek().getArrival_time() == current_time){
            Event event = workLoad.poll();
            String log = String.format("\tArrival: %s pages from %s at %s seconds",
                    event.getJob().getNumber_of_pages(), event.getJob().getUser(), event.getArrival_time());
            log_printer.println(log);
            dispatch(event);
        }
    }

    private void dispatch(Event job){
        Printer lessWorkPrinter = printers[0];
        for (int i = 1; i < printers.length; i++){
            if (lessWorkPrinter.getTotalWorkProcess() > printers[i].getTotalWorkProcess())
                lessWorkPrinter = printers[i];
        }
        lessWorkPrinter.addDispatchedJob(job);
    }

    private String analyzeLatencyTimes(){
        LinkedList<Integer> latencyTimes = getLatencyTimes();
        int total_jobs = latencyTimes.size();
        int aggregate_latency = 0;
        for (int time : latencyTimes)  aggregate_latency += time;
        double mean_latency = (double)aggregate_latency / (double)total_jobs;

        return String.format("\n\tTotal_jobs: %d" +
                        "\n\tAggregate latency:%d seconds" +
                        "\n\tMean latency:%.4f seconds",
                total_jobs, aggregate_latency, mean_latency);
    }

    private LinkedList<Integer> getLatencyTimes(){
        LinkedList<Integer> latencyTimes = new LinkedList<>();
        for (Printer printer : printers)
            latencyTimes.addAll(printer.getLatencyTimes());
        return latencyTimes;
    }

    private class Printer{

        private int progress_bar;
        private Event current_event;
        private AbstractQueue<Event> workflow;
        private LinkedList<Integer> latencyTimes;
        private PrintWriter log_printer;
        private int totalWorkProgress;

        public Printer(String workPattern, PrintWriter log_printer){
            if (workPattern.equals("Priority"))  workflow = new MyPriorityQueue<>();
            else                                 workflow = new MyQueue<>();
            this.log_printer = log_printer;
            latencyTimes = new LinkedList<>();
        }

        public void work(){
            // 查看是否需要执行添加任务的操作
            if       (!hasJob())       tryToGetWork();
            else if  (isFinished())    moveToNextWork();
            // 执行任务
            if (hasJob())  doJob();

        }

        public void addDispatchedJob(Event event){
            workflow.add(event);
            int progress = event.getJob().getNumber_of_pages()*seconds_per_page;
            totalWorkProgress += progress;
        }

        public  boolean               isFree()              { return !hasJob() && workflow.isEmpty();}
        public  int                   getTotalWorkProcess() { return totalWorkProgress;}
        public  LinkedList<Integer>   getLatencyTimes()     { return latencyTimes;}
        private boolean               hasJob()              { return current_event != null;}

        private void doJob(){
            progress_bar++;
            totalWorkProgress--;
        }

        private boolean isFinished(){
            int total_process = current_event.getJob().getNumber_of_pages() * seconds_per_page;
            return progress_bar == total_process;
        }

        private void moveToNextWork(){
            endCurrentWork();
            tryToGetWork();
        }

        private void endCurrentWork(){
            progress_bar = 0;
            latencyTimes.add(countWaitTime(current_event));
            current_event = null;
        }

        private void tryToGetWork(){
            if (workflow.isEmpty()) return;
            current_event = workflow.poll();
            String log = String.format("\tServicing: %d pages from %s at %d seconds",
                    current_event.getJob().getNumber_of_pages(),
                    current_event.getJob().getUser(),
                    current_time);
            log_printer.println(log);
        }

        private int countWaitTime(Event event){
            int solve_time = event.getJob().getNumber_of_pages() * seconds_per_page;
            int latency_time = getCurrentTime() - event.getArrival_time() - solve_time;
            return latency_time;
        }
    }
}
