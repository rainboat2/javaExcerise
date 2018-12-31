package myAPI;

import java.io.*;
import java.util.Arrays;

public class Test {

    private static class t implements Serializable{
        int[] a = new int[]{1, 3, 5, 5};
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("run");
        t s = new t();
        System.out.println(Arrays.toString(s.a));
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.ser"));
        out.writeObject(s);
        out.flush();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("test.ser"));
        s = (t) in.readObject();
        System.out.println(Arrays.toString(s.a));
    }

}
