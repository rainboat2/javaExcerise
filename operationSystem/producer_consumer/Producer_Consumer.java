package producer_consumer;

import myAPI.CircularQueue;

import java.util.concurrent.Semaphore;

public class Producer_Consumer {

    public static void main(String[] args){new Producer_Consumer().run();}

    private static final int PRODUCTION_TIME = 1000;
    private static final int CONSUME_TIME = 1000;
    private static final int BUFFER_SIZE = 5;

    private Semaphore mutex = new Semaphore(1);
    private Semaphore empty = new Semaphore(BUFFER_SIZE);   //表示空的货物架数量
    private Semaphore full = new Semaphore(0);      //表示有货的货物架数量

    private CircularQueue<Integer> buffer = new CircularQueue<>(BUFFER_SIZE);
    private int count = 0;

    public void run(){
//        Thread producer = new Thread(new Producer());
//        Thread consumer = new Thread(new Consumer());
//        producer.start();
//        consumer.start();
        for (int i = 0; i < 2; i++){
            new Thread(new Producer()).start();
            new Thread(new Consumer()).start();
        }
    }

    public void acquire(Semaphore s){
        try {
            s.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void release(Semaphore s){
        s.release();
    }

    private class Producer implements Runnable{

        public void run(){
            while (true){
                int good = product();
                getEmpty();
                acquire(mutex);
                buffer.add(good);
                release(full);  //满货物架加一
                System.out.printf("货物%d成功放置在缓冲区\n", good);
                release(mutex);
            }
        }

        private int product(){
            try {
                Thread.sleep((int)(Math.random()*PRODUCTION_TIME));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            int index = ++count;
            System.out.printf("货物%d被生产\n", index);
            return index;
        }

        private void getEmpty(){
            if (!empty.tryAcquire()){
                System.out.println("货物架已满，生产者开始等待");
                acquire(empty);
                System.out.println("一个空货架被空出");
            }
        }
    }

    private class Consumer implements Runnable{

        public void run(){
            while (true){
                getFull();
                acquire(mutex);
                int good = buffer.poll();
                System.out.printf("货物%d被消费\n", good);
                release(empty);
                release(mutex);
                consumer();  //消费者需要一点时间来消费货物
            }
        }

        public void getFull(){
            if (!full.tryAcquire()){
                System.out.println("所有的货架都是空的，消费者开始等待");
                acquire(full);
                System.out.println("消费者等到了一个有货的货架");
            }
        }

        private void consumer(){
            try {
                Thread.sleep((int)(Math.random()*CONSUME_TIME));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
