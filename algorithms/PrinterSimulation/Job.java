package PrinterSimulation;

public class Job {

    private String user;
    private int number_of_pages;

    public Job(String user, int pages){
        this.user = user;
        this.number_of_pages = pages;
    }

    public String getUser() {
        return user;
    }

    public int getNumber_of_pages() {
        return number_of_pages;
    }

}
