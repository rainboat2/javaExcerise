package PrinterSimulation;

import java.io.*;

public class Simulator {

    protected int seconds_per_page;
    protected MyQueue<Event> workLoad;

    public Simulator(){
        this.seconds_per_page = 2;
        workLoad = new MyQueue<>();
    }

    public void loadWork(String path){
        File file = new File(path);
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null)
                addToWorkLoad(line);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToWorkLoad(String line){
        if (line.startsWith("#")) return;
        // [time page user]
        String[] info = line.split(" ");
        int arrive_time = Integer.parseInt(info[0]);
        int pages = Integer.parseInt(info[1]);
        String user = info[2];
        Event event = new Event(new Job(user, pages), arrive_time);
        workLoad.add(event);
    }
}
