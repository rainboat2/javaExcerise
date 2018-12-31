package myAPI;

import java.util.*;

public class CircularQueue<Type> implements MyAbstractQueue<Type> {

    public static void main(String[] args){
        CircularQueue<Integer>  queue = new CircularQueue<>(4);
        for (int i = 0; i < 4; i++)
            queue.add(i);
        System.out.println(queue.isFull());
    }

    private int front;
    private int rear;
    private Type[] array;

    public CircularQueue(int size)  { array = (Type[]) new Object[size+1]; } //数组大小必需要比循环队列的大小大一
    public CircularQueue()          { this(10);}

    public boolean isEmpty()  { return front == rear;}
    public boolean isFull()   { return (rear+1)%array.length == front;}
    public int     size()     { return (rear - front + array.length)%array.length;}

    public void add(Type element){
        if (isFull())
            throw new OutOfMemoryError();
        array[rear] = element;
        rear = (rear + 1) % array.length;
    }

    public Type poll(){
        if (isEmpty())
            throw new NoSuchElementException();
        Type element = array[front];
        front = (front + 1) % array.length;
        return element;
    }

    public Type peek(){
        if (isEmpty())
            throw new NoSuchElementException();
        return array[front];
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (int i = front; i != rear; i = (i+1)%array.length){
            s.append(i);
            s.append(" ");
        }
        return s.toString();
    }

    public Iterator<Type> iterator() {
        return new MyIterator<Type>();
    }

    private class MyIterator<Typ> implements Iterator<Type>{
        int index = front;

        public boolean hasNext() {
            return index == rear;
        }

        public Type next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Type t = array[front];
            index = (index + 1) % array.length;
            return t;
        }
    }
}
