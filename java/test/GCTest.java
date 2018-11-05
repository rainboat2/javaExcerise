package test;

public class GCTest {

    public static void main(String[] args){
        Book novel = new Book(true);
        novel.checkIn();
        new Book(true);
        System.gc();
    }

    public static void t(){

    }
}

class Book{

    boolean checkedOut = false;

    Book(boolean checkOut){
        checkedOut = checkOut;
    }

    void checkIn(){
        checkedOut = false;
    }

    protected void finalize(){
        if (checkedOut)
            System.out.println("erro: checkout");
    }
}