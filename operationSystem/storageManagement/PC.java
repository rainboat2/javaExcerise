package storageManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PC {

    private Scanner s;

    public PC(){
        File file = new File("myFile/os/instructions.txt");
        try {
            s = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasNext(){
        return s.hasNext();
    }

    public Instruction next(){
        if (!s.hasNext())
            throw new NoSuchElementException();
        String line = s.nextLine();
        while (line.startsWith("#")) line = s.nextLine();
        String[] info = line.split(",");
        return new Instruction(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]));
    }

    public class Instruction{
        private String operation;
        private int pageIndex;
        private int offset;

        public Instruction(String operation, int pageIndex, int offset) {
            this.operation = operation;
            this.pageIndex = pageIndex;
            this.offset = offset;
        }

        public String getOperation() {
            return operation;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public int getOffset() {
            return offset;
        }
    }
}
