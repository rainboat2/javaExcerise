package fileSystem;


import java.io.*;
import java.util.ArrayList;

/* 主目录
 * 保存用户文件夹
 */
public class MFD {

    private static final int MAX_USERS = 5;

    private Entry[] users = new Entry[MAX_USERS];

    private class Entry {
        String userName;
        UFD ufd;

        public Entry(String userName, UFD ufd) {
            this.userName = userName;
            this.ufd = ufd;
        }
    }

    public MFD(){
        loadEntry();
    }

    public UFD search(String userName){
        for (Entry e : users)
            if (e.userName.equals(userName))
                return e.ufd;
        return null;
    }

    public void showUsers(){
        for (int i = 0; i < 5; i++)
            System.out.printf("%d. %s\n", i, users[i].userName);
    }

    public void close(){
        saveEntry();
    }

    private void saveEntry(){
        File file = new File("myFile/os/file catalog.txt");
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(file));
            for (Entry e : users){
                w.write(e.userName );
                w.newLine();
                for (String line : e.ufd.allFiles())
                    w.write(line + "\n");
                w.write("<<");
                w.newLine();
            }
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEntry(){
        File file = new File("myFile/os/file catalog.txt");
        MyReader r = new MyReader(file);
        String line;
        int index = 0;
        while ((line = r.nextLine()) != null && index < MAX_USERS){
            String userName = line;
            users[index++] = new Entry(userName, getUFD(r));
        }
        r.close();
    }

    private UFD getUFD(MyReader r) {
        ArrayList<String> files = new ArrayList<>();
        String line;
        while (true){
            line = r.nextLine();
            if (line.equals("<<")) break;
            files.add(line);
        }
        return new UFD(files);
    }


    private static class MyReader {

        private BufferedReader r;

        public MyReader(File file){
            try {
                r = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public String nextLine(){
            String line = null;
            try {
                line = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return line;
        }

        public void close(){
            try {
                r.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
