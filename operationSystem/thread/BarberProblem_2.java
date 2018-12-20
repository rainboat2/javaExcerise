package thread;

import myAPI.CircularQueue;

import java.util.concurrent.Semaphore;

public class BarberProblem_2 {

    public static void main(String[] args){ new BarberProblem_2().begin();}

    private static final int MAX = 5;
    private static final int WORK_TIME = 2000;
    private static final int COME_TIME = 1000;

    private Semaphore barber = new Semaphore(0);
    private Semaphore chairs = new Semaphore(MAX);  //表示所剩的椅子数量
    private CircularQueue<Integer> waitCustomers = new CircularQueue<>();
    private Semaphore mutex = new Semaphore(1);

    private static int count = 0;

    public void begin(){
        Thread barber = new Thread(new Barber());
        barber.start();

        while (true){
            try {
                Thread.sleep((int)(Math.random()*COME_TIME));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new Customer()).start();
        }
    }

    private void acquire(Semaphore s){  //使信号量减一
        try {
            s.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void release(Semaphore s){ //使信号量加一
        s.release();
    }

    private class Barber implements Runnable{

        public void run(){
            while (true){
                acquire(barber);    //等待被唤醒
                acquire(mutex);     //上锁，确保互斥
                release(chairs);    //获取一个用户
                cutHairs();
                if (!waitCustomers.isEmpty()) release(barber);
                else                          System.out.println("店内空闲，理发师开始睡觉");
                release(mutex);
            }
        }

        private void cutHairs(){
            int index = waitCustomers.deQueue();
            System.out.printf("理发师开始为顾客%d服务\n", index);
            try {
                Thread.sleep(WORK_TIME);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.printf("顾客%d理发结束\n", index);
        }
    }

    private class Customer implements Runnable{

        private int index;

        public Customer(){
            index = ++count;
            System.out.printf("顾客%d进入理发店\n", index);
        }

        public void run(){
            if (chairs.tryAcquire()){
                System.out.printf("顾客%d开始等待理发师\n", index);
                waitCustomers.enQueue(index);
                acquire(mutex);
                if (barber.availablePermits() == 0){
                    barber.release();
                    System.out.println("理发师被唤醒");
                }
                release(mutex);
            }else{
                System.out.printf("店内人数过多，顾客%d离开\n", index);
            }
        }
    }
}
