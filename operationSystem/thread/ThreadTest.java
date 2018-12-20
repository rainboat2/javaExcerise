package thread;

import java.util.Random;

public class ThreadTest {
    public static void main(String[] args){
        Random random = new Random(20);
        int[] num = new int[5];
        for (int i = 0; i < num.length; i++)
            num[i] = random.nextInt(100);
        Sum sum = new Sum();
        Thread thread = new Thread(new Summation(num[3], sum));
        thread.start();
        try {
            thread.join();
            System.out.println("sum of " + num[3] + " is" + sum.getSum());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Sum{
    private int sum;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}

class Summation implements Runnable{

    private int upper;
    private Sum sumValue;

    public Summation(int upper, Sum sumValue) {
        this.upper = upper;
        this.sumValue = sumValue;
    }

    public void run(){
        int sum = 0;
        for (int i = 0; i < upper; i++) {
            sum += i;
        }
        sumValue.setSum(sum);
    }
}