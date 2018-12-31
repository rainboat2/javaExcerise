package fileSystem;

public class UserFile {

    private String fileName;
    private boolean write;
    private boolean read;
    private boolean execute;
    private int length;

    public UserFile(String fileName){
        this(fileName, true, true, true, 0);
    }

    public UserFile(String fileName, boolean write, boolean read, boolean execute, int length) {
        this.fileName = fileName;
        this.write = write;
        this.read = read;
        this.execute = execute;
        this.length = length;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public int getLength(){
        return length;
    }

    public void setLength(int length){
        this.length = length;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(fileName).append(",");
        if (write)   s.append(1).append(",");
        else         s.append(0).append(",");

        if (read)    s.append(1).append(",");
        else         s.append(0).append(",");

        if (execute) s.append(1).append(",");
        else         s.append(0).append(",");

        s.append(length);
        return s.toString();
    }
}
