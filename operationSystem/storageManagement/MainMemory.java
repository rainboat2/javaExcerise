package storageManagement;

import myAPI.CircularQueue;

import java.io.*;

public class MainMemory {

    public static void main(String[] args){
        MainMemory m = new MainMemory();
        m.run();
    }

    private Page[] pageTable;
    private CircularQueue<Page> queue;

    public MainMemory(){
        queue = new CircularQueue<>(4);
        pageTable = new Page[7];
        loadTask();
    }

    public void run(){
        PC pc = new PC();
        while (pc.hasNext()){
            PC.Instruction instruction = pc.next();
            int L = instruction.getPageIndex();
            while (true){
                Page p = getPage(L);
                if (p.isSignal() == Page.IN_MAIN_MEMORY){
                    execute(instruction, p);
                    showAddress(p, instruction.getOffset());
                    break;
                }
                else
                    swap(p);
            }

        }
    }

    private Page getPage(int pageIndex){
        for (int i = 0; i < pageTable.length; i++)
            if (pageTable[i].getPageIndex() == pageIndex)
                return pageTable[i];
        return null;
    }

    private void showAddress(Page p, int offset){
        int memoryIndex = p.getMemoryIndex();
        StringBuilder s = new StringBuilder();
        s.append(memoryIndex);
        if      (offset < 10)  s.append("00").append(offset);
        else if (offset < 100) s.append("0").append(offset);
        System.out.println(s.toString());
    }

    private void execute(PC.Instruction instruction, Page p){
        if (instruction.getOperation().equals("存"))
            p.setChange(true);
    }

    private void swap(Page p){
        int memoryIndex = p.getMemoryIndex();
        if (queue.isFull()){
            Page old = queue.poll();
            if (old.isChange()) {
                System.out.println("OUT " + old.getPageIndex());
                old.setChange(false);
            }
            old.setSignal(Page.NOT_IN_MAIN_MEMORY);
            memoryIndex = old.getMemoryIndex();
        }
        queue.add(p);
        p.setMemoryIndex(memoryIndex);
        p.setSignal(Page.IN_MAIN_MEMORY);
        System.out.println("IN " + p.getPageIndex());
    }

    private void loadTask(){
        File file = new File("myFile/os/task.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null)
                addTask(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTask(String line){
        if (line.startsWith("#")) return;
        String[] info = line.split(",");
        int memoryIndex = -1;
        if (!info[2].equals("")) memoryIndex = Integer.parseInt(info[2]);
        boolean signal = false;
        if (Integer.parseInt(info[1]) == 1) signal = true;
        Page p = new Page(Integer.parseInt(info[0]), signal, memoryIndex, Integer.parseInt(info[4]));

        if (signal) queue.add(p);
        pageTable[p.getPageIndex()] = p;
    }
}
