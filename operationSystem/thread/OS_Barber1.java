package thread;

import java.util.concurrent.Semaphore;

public class OS_Barber1{
    private static int customers = 0;
    private static final int MAX = 5;
    private static int busy = 0;
    private static Semaphore sem=new Semaphore(1);

    public static void main(String args[]) throws InterruptedException{
        OS_Barber1 bar=new OS_Barber1();
        for(int i=1;i<=20;i++){
            new Thread(new Bar1(bar,i)).start();
            Thread.sleep((int) (400 - Math.random() * 300));
        }
    }
    public synchronized boolean isFull() {
        if (customers == MAX) {
            return true;
        }
        return false;
    }

    public synchronized boolean isEmpty() {
        if (customers == 0) {
            return true;
        }
        return false;
    }

    public synchronized boolean isBusy() {
        if (busy == 1) {
            return true;
        }
        return false;
    }

    public  void Gobar(int index) throws InterruptedException{


        System.out.println("Con"+index+" is coming");
        customers++;
        //判断是否满
        if(isFull()){
            System.out.println("Is full,"+"Con"+index+" is leaving");
            customers--;
        }else{
            if(busy==1){
                System.out.println("Con"+index+" is waitting");
            }
            //sem.acquire();
            synchronized (this){
                while(busy==1){
                    //若有人在理发，则等待
                    wait();
                }
            }

            if(customers ==1){
                System.out.println("Con"+index+" is wake");
            }
            busy=1;
            System.out.println("Con"+index+" is Serving");
            Thread.sleep(1000);
            System.out.println("Con"+index+" is leaving");
            customers--;
            //sem.release();
            synchronized (this){
                busy=0;
                //唤醒
                notify();
            }

            if(customers ==0){
                System.out.println("Bar is sleep");
            }
        }
    }
}

class Bar1 implements Runnable {
    private OS_Barber1 ob;
    private int index;

    public Bar1(OS_Barber1 ob,int i) {
        this.ob = ob;
        index=i;
    }

    @Override
    public void run() {
        try {
            ob.Gobar(index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

