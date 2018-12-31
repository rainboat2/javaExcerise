package fileSystem;

import java.util.Scanner;

public class FileSystem {

    public static void main(String[] args) {
        FileSystem f = new FileSystem();
        f.run();
    }

    private MFD mfd;
    private Scanner s;

    public FileSystem(){
        mfd = new MFD();
        s = new Scanner(System.in);
    }

    public void run(){
        mfd.showUsers();
        while (true){
            System.out.print(">>");
            String[] cmd = s.nextLine().split(" ");
            if (cmd[0].equals("exit")) break;
            if (cmd.length == 2 && cmd[0].equals("login")){
                runUser(cmd[1]);
            }else {
                System.out.println("输入指令格式错误");
            }
        }
        mfd.close();
    }

    private void runUser(String userName){
        UFD ufd = mfd.search(userName);
        if (ufd == null){
            System.out.println("该用户目录不存在!");
            return;
        }
        AFD afd = new AFD(ufd);
        afd.showFiles();
        while (true){
            System.out.print(">>");
            String[] cmd = s.nextLine().split(" ");
            if (cmd[0].equals("exit")) break;
            if (cmd.length == 2)
                afd.run(cmd[0], cmd[1]);
            else
                System.out.println("指令格式错误");
        }
        afd.showFiles();
    }
}
