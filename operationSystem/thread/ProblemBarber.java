package thread;

import java.util.concurrent.Semaphore;

public class ProblemBarber {

    public static void main(String[] args){
        Semaphore signal = new Semaphore(10);
        Semaphore sleep = new Semaphore(0);
        Thread barber = new Thread(new Barber(signal, sleep));

        barber.start();
        try{
            // 每间隔一段时间就会有顾客来
            Thread.sleep(1000);
            while (true){
                Thread customer = new Thread(new Customer(signal, sleep));
                customer.start();
                Thread.sleep((int)(Math.random()*10000));
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static class Barber implements Runnable{
        Semaphore signal;
        Semaphore sleep;

        public Barber(Semaphore signal, Semaphore sleep){
            this.sleep = sleep;
            this.signal = signal;
        }

        public void run(){
            synchronized (this){
                try {
                    sleep.acquire();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                while (signal.availablePermits() > 0){
                    System.out.println("开始为一名客户理发！");
                    try{
                        Thread.sleep(5000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("一名客户理发结束！");
                    signal.release();
                }
            }
        }
    }

    public static class Customer implements Runnable{
        Semaphore signal;
        Semaphore sleep;

        public Customer(Semaphore signal, Semaphore sleep){
            this.signal = signal;
            this.sleep = sleep;
        }

        public void run(){
            synchronized (this){
                if (signal.tryAcquire()){
                    System.out.println("新添加一名用户！");
                    if (signal.availablePermits() == 9)
                        sleep.release(); //顾客唤醒理发师
                }else {
                    System.out.println("座椅不够，顾客离开！");
                }
            }
        }
    }
}
