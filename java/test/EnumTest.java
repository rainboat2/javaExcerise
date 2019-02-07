package test;

public class EnumTest {

    public static void main(String[] args) {
        System.out.println(Week.valueOf("Monday"));
        for (Week w : Week.values())
            System.out.println(w);
    }

}


enum Week{

    Monday, Tuesday;
}