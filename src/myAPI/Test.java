package myAPI;

import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        File file = new File("result.out");
        file.renameTo(new File("test"));
        file.createNewFile();
        System.out.println(file.getPath());
    }

}
