package PrinterSimulation;

import java.util.NoSuchElementException;

public class MyQueue<Type>{

    public static void main(String[] args){
        MyQueue<Integer> queue = new MyQueue<>();
        for (int j = 0; j < 6; j++){
            for (int i = 0; i < 10; i++)
                queue.enQueue(i);
            for (int i = 0; i < 10; i++)
                System.out.print(queue.deQueue());
            System.out.println();
            System.out.println(queue.isEmpty());
        }

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
    public boolean isEmpty() { return first == last;}


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
        return first.next.value;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (Node temp = first.next; temp != null; temp = temp.next)
            builder.append(temp);
        return builder.toString();
    }
}
