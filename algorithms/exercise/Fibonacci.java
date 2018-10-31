package exercise;

public class Fibonacci {

    public static void main(String[] args){
        int a = 9;
        System.out.println(fib(a));
        System.out.println(fibIterator(a));
    }

    //1 1 2 3 5 8 13 21 34
    public static int fib(int n){
        if      (n == 1)   return 1;
        else if (n == 2)   return 1;
        else               return fib(n-1) + fib(n-2);
    }

    public static int fibIterator(int n){
        int a = 1, b = 1;
        //进行n-2次迭代后，b的值就是n处的值
        for (int i = 0; i < n-2; i++){
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
}
