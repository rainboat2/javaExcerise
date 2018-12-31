package PrinterSimulation;


import myAPI.MyAbstractQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyQueue<Type> implements MyAbstractQueue<Type> {

    private Node first;
    private Node last;
    int size;

    private class Node{
        Type value;
        Node next;

        public Node(Type value){
            this.value = value;
        }
    }

    public MyQueue()         { clear();}

    public int size() {
        return size;
    }

    public boolean isEmpty() { return first == last;}


    public void clear(){
        first = last = new Node(null);
        first.next = last;
        size = 0;
    }

    public void add(Type type) {
        last.next = new Node(type);
        last = last.next;
        size++;
    }


    public Type poll() {
        if (isEmpty())
            throw new NoSuchElementException();
        first = first.next;
        size--;
        return first.value;
    }

    public Type peek(){
        if (isEmpty())
            throw new NoSuchElementException();
        return first.next.value;
    }

    @Override
    public Iterator<Type> iterator() {
        return null;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (Node temp = first.next; temp != null; temp = temp.next)
            builder.append(temp);
        return builder.toString();
    }
}
