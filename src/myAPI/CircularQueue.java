package myAPI;

import java.util.NoSuchElementException;

public class CircularQueue<Type> {

    public static void main(String[] args){
        CircularQueue<Integer>  queue = new CircularQueue<>();
        for (int i = 0; i < 9; i++)
            queue.enQueue(i);
        System.out.println(queue);
        for (int i = 0; i < 5; i++)
            queue.deQueue();
        System.out.println(queue);
        for (int i = 0 ; i < 5; i++)
            queue.enQueue(i);
        System.out.println(queue);
    }

    private int front;
    private int rear;
    private Type[] array;

    public CircularQueue(int size)  { array = (Type[]) new Object[size]; }
    public CircularQueue()          { this(10);}

    public void clear(){
        array = (Type[]) new Object[array.length];
        front = rear = 0;
    }

    public boolean isEmpty()  { return front == rear;}
    public boolean isFull()   { return (rear+1)%array.length == front;}
    public int     size()     { return (rear - front + array.length)%array.length;}

    public void enQueue(Type element){
        if (isFull())
            throw new NoSuchElementException();
        array[rear] = element;
        rear = (rear + 1) % array.length;
    }

    public Type deQueue(){
        if (isEmpty())
            throw new NoSuchElementException();
        Type element = array[front];
        front = (front + 1) % array.length;
        return element;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (int i = front; i != rear; i = (i+1)%array.length){
            s.append(i);
            s.append(" ");
        }
        return s.toString();
    }
}
