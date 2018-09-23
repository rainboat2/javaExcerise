package excerise;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

public class Test<Type> {

    public static void main(String[] args){
        PrintWriter writer = null;
        try {
            FileOutputStream out = new FileOutputStream("test.txt");
            writer = new PrintWriter(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.println("test");
        writer.flush();
    }

}
