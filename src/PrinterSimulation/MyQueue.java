package PrinterSimulation;

import java.util.NoSuchElementException;

public class MyQueue<Type> {

    public static void main(String[] args){
        MyQueue<Integer> queue = new MyQueue<>();
        queue.enQueue(1);
        queue.deQueue();
        queue.enQueue(2);
        queue.enQueue(3);
        queue.deQueue();
    }

    private Node first;
    private Node last;

    private class Node{
        Type value;
        Node next;

        public Node(Type value){
            this.value = value;
        }
    }

    public MyQueue()         { clear();}
    public boolean isEmpty() { return first == null;}


    public void clear(){
        first = last = new Node(null);
        first.next = last;
    }

    public void enQueue(Type value){
        last.next = new Node(value);
        last = last.next;
    }

    public Type deQueue(){
        if (isEmpty())
            throw new NoSuchElementException();
        first = first.next;
        return first.value;
    }

    public Type peek(){
        if (isEmpty())
            throw new NoSuchElementException();
        return first.value;
    }
}
