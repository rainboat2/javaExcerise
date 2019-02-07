package fileSystem;

import java.util.LinkedList;

/*
 * AFD 运行文件目录
 * 用于运行UFD，定义对每一个文件的操作
 */
public class AFD {

    private UFD ufd;
    private LinkedList<UserFile> openFiles;

    public AFD(UFD ufd){
        this.ufd = ufd;
        openFiles = new LinkedList<>();
    }

    public void run(String operation, String fileName){
        UserFile uf = ufd.search(fileName);
        if (uf == null && !operation.equals("create")){
            System.out.printf("文件%s不存在！\n", fileName);
            return;
        }
        switch (operation){
            case "create":
                create(fileName);
                break;
            case "delete":
                delete(uf);
                break;
            case "open":
                execute(uf);
                break;
            case "close":
                close(uf);
                break;
            case "read":
                read(uf);
                break;
            case "write":
                write(uf);
                break;
            default:
                System.out.println("未知操作！");

        }
    }

    public void showFiles(){
        ufd.showFiles();
    }

    private void write(UserFile uf){
        if (! uf.isWrite()){
            System.out.println("无写入权限！");
            return;
        }
        uf.setLength(uf.getLength() + 1);
        System.out.println("成功写入");
    }

    private void read(UserFile uf){
        if (! uf.isRead()){
            System.out.println("无读取权限!");
            return;
        }
        System.out.println(uf.toString());
    }

    private void execute(UserFile uf){
        if (! uf.isExecute()){
            System.out.println("无打开该文件的权限");
            return;
        }
        if (openFiles.size() >= 5){
            System.out.println("打开文件数量达到上限");
            return;
        }
        openFiles.add(uf);
        System.out.println("成功打开文件");
    }

    private void create(String fileName){
        if (ufd.search(fileName) != null) {
            System.out.println("创建失败，该文件名已存在！");
            return;
        }
        if (ufd.isFull()){
            System.out.println("创建失败，文件数量达到上限");
            return;
        }
        ufd.add(new UserFile(fileName));
        System.out.println("成功添加文件");
    }

    private void close(UserFile uf){
        boolean flag = openFiles.removeIf(file -> file.equals(uf));
        if   (flag) System.out.printf("成功关闭所有打开文件%s的程序\n", uf.getFileName());
        else        System.out.println("并无程序打开当前文件");
    }

    private void delete(UserFile uf){
        if (ufd.search(uf.getFileName()) == null) {
            System.out.println("目录中无该文件");
            return;
        }
        ufd.delete(uf);
        System.out.println("成功删除");
    }
}
