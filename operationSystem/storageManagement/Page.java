package storageManagement;

public class Page {

    public static final boolean IN_MAIN_MEMORY = true;
    public static final boolean NOT_IN_MAIN_MEMORY = false;

    private int pageIndex;
    private boolean signal;
    private int memoryIndex;
    private boolean change;
    private int position;

    public Page(int pageIndex, boolean signal, int memoryIndex, int position) {
        this.pageIndex = pageIndex;
        this.signal = signal;
        this.memoryIndex = memoryIndex;
        this.position = position;
        this.change = false;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public boolean isSignal() {
        return signal;
    }

    public void setSignal(boolean signal) {
        this.signal = signal;
    }

    public int getMemoryIndex() {
        return memoryIndex;
    }

    public void setMemoryIndex(int memoryIndex) {
        this.memoryIndex = memoryIndex;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setChange(boolean change){
        this.change = change;
    }

    public boolean isChange(){
        return change;
    }
}
