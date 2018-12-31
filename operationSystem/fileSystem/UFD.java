package fileSystem;

import java.util.ArrayList;

/*
 * 用户文件夹
 * 内容[<"文件名", "保护码", "文件长度">，……]
 * 保护码：读、写、执行
 */
public class UFD {

    private static final int MAX_FILES = 10;

    private UserFile[] files = new UserFile[MAX_FILES];
    private int number;


    public UserFile search(String fileName){
        int index = indexOf(fileName);
        if (index == -1) return null;
        else             return files[index];
    }

    public boolean isFull(){
        return number == MAX_FILES;
    }

    public void add(UserFile file){
        if (isFull())
            throw new IndexOutOfBoundsException();
        files[number++] = file;
    }

    public void delete(UserFile uf){
        int i = indexOf(uf.getFileName());
        if (i == -1) return;
        for (int j = i; j < number-1; j++)
            files[j] = files[j+1];
        number--;
    }

    public void showFiles(){
        for (int i = 0; i < number; i++){
            UserFile uf = files[i];
            System.out.printf("%d. %s, 读：%s,写：%s,执行：%s,长度：%d\n", i, uf.getFileName(), uf.isRead(),
                               uf.isWrite(), uf.isExecute(), uf.getLength());
        }
    }

    private int indexOf(String fileName){
        for (int i = 0; i < number; i++)
            if (files[i].getFileName().equals(fileName))
                return i;
        return -1;
    }

    public UFD(ArrayList<String> files){
        //文件名，写，读，执行，长度
        if (files.size() > MAX_FILES)
            throw new IndexOutOfBoundsException("文件数不能大于10");
        for (int i = 0; i < files.size(); i++)
            this.files[i] = getFile(files.get(i));
        number = files.size();
    }

    private UserFile getFile(String line){
        //文件名，写，读，执行，长度
        String[] info = line.split(",");
        boolean write = info[1].equals("1");
        boolean read = info[2].equals("1");
        boolean execute = info[3].equals("1");
        int length = Integer.parseInt(info[4]);
        return new UserFile(info[0], write, read, execute, length);
    }

    public Iterable<String> allFiles(){
        ArrayList<String> list = new ArrayList<>(number);
        for (int i = 0; i < number; i++)
            list.add(files[i].toString());
        return list;
    }
}
