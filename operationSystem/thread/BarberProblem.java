package thread;

import myAPI.CircularQueue;

public class BarberProblem {

    private final static int MAX = 5;

    private static CircularQueue<Integer> chairs = new CircularQueue<>(MAX);
    private static int barber = 0;
    private static int mutex = 1;

    public static void main(String[] args){
        Thread barber = new Thread(new Barber());
        barber.start();

        while (true){
            try {
                Thread.sleep((int)(Math.random()*2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread customer = new Thread(new Customer());
            customer.start();
        }
    }

    private static class Barber implements Runnable{

        public void run(){
            while (true){
                while (barber <= 0) { Thread.onSpinWait();}
                waitMutex();
                int index = chairs.deQueue();
                signalMutex();

                workForCustomer(index);

                waitMutex();
                if (chairs.size() == 0) barber = 0;
                if (barber <= 0) System.out.println("店内空闲，理发师开始睡觉！");
                signalMutex();
            }
        }

        private void waitMutex(){
            while (mutex <= 0) { Thread.onSpinWait();}
            mutex--;
        }

        private void signalMutex(){
            mutex++;
        }

        private void workForCustomer(int index){
            System.out.printf("理发师开始为顾客%d理发\n", index);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("顾客%d理发结束\n", index);
        }
    }

    private static class Customer implements Runnable{

        static int count = 0; //记录到来的顾客数量

        private int index;

        public Customer(){
            index = ++count;
            System.out.printf("顾客%d来到理发店\n", index);
        }

        public void run(){
            waitMutex();
            if (chairs.isFull())  leave();
            else                  waitBarber();
            signalMutex();
        }

        private void waitMutex(){
            while (mutex <= 0) { Thread.onSpinWait();}
            mutex--;
        }

        private void signalMutex(){
            mutex++;
        }

        private void waitBarber(){
            chairs.enQueue(index);
            System.out.printf("顾客%d开始等待理发师\n", index);
            if (barber <= 0) {
                barber++;
                System.out.println("理发师被唤醒！");
            }
        }

        private void leave(){
            System.out.printf("人数过多，顾客%d离开\n", index);
        }
    }
}
