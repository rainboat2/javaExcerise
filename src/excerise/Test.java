package excerise;

public class Test<Type> {

    private class T<Type>{
        Type t;
    }

    public void test(){
        T[] a = new T[2];
    }
}
