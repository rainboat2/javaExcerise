package Thread;

import java.util.concurrent.Semaphore;

/*
 * 信号量
 * 临界区：理发师、椅子
 * 通过使用mutex信号量来同步访问临界区的线程
 */

public class BarberProblem_3 {

    public static void main(String[] args){ new BarberProblem_3().beginToWork();}

    private static final int CHAIRS = 5;
    private static final int WORK_TIME = 1000;
    private static final int COME_TIME = 1000;

    private int waiting = 0;   //表示理发的顾客数
    private Semaphore customers = new Semaphore(0);
    private Semaphore barber = new Semaphore(0);
    private Semaphore mutex = new Semaphore(1);

    private static int count = 0;

    public void beginToWork(){
        System.out.println("-----开门营业--------");
        Thread barber = new Thread(new Barber());
        barber.start();

        while (true){
            try {
                Thread.sleep((int)(Math.random()*COME_TIME));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread customer = new Thread(new Customer());
            customer.start();
        }
    }

    public void acquire(Semaphore s){ //获取一个信号量
        try {
            s.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void release(Semaphore s){ //释放一个信号量
        s.release();
    }

    private class Barber implements Runnable{

        public void run(){
            while (true){
                tryToSleep();
                acquire(mutex);
                waiting--;
                release(barber);
                release(mutex);
                cut_hair();
            }
        }

        public void tryToSleep(){
            try {
                Thread.sleep(2);   //理发师需要一点时间看一下店面
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!customers.tryAcquire()){ //获取一个顾客信号量，若没有则睡觉
                System.out.println("没有顾客，理发师开始睡觉");
                acquire(customers);
                System.out.println("顾客将理发师唤醒");
            }
        }

        public void cut_hair(){
            try {
                Thread.sleep(WORK_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class Customer implements Runnable{

        private int index = ++count;

        public void run(){
            System.out.printf("顾客%d进入理发店\n", index);
            acquire(mutex);
            if (waiting == CHAIRS) leave();
            else                   waitBarber();
        }

        private void leave(){
            release(mutex);
            System.out.printf("店内人数过多，顾客%d离开\n", index);
        }

        private void waitBarber(){
            waiting++;
            release(customers);
            release(mutex);    //释放锁
            acquire(barber);   //等待一个理发师
            getHairCut();
        }

        private void getHairCut(){
            System.out.printf("顾客%d开始理发\n", index);
            try {
                Thread.sleep(WORK_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("顾客%d理发结束，离开理发店\n", index);
        }
    }
}
