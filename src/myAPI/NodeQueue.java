package myAPI;

import java.util.NoSuchElementException;

public class NodeQueue<Type> {

    public static void main(String[] args){
        NodeQueue<Integer> queue = new NodeQueue<>();
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

    private Node<Type> first;
    private Node<Type> rear;

    private class Node<Type>{
        Type element;
        Node<Type> next;

        public Node(Type element, Node next){
            this.element = element;
            this.next    = next;
        }
    }

    public NodeQueue()        { clear();}
    public void    clear()    { first = rear = new Node<>(null, null);}
    public boolean isEmpty()  { return first == rear;}

    public void enQueue(Type element){
        rear.next = new Node<>(element, null);
        rear = rear.next;
    }

    public Type deQueue(){
        if (isEmpty())
            throw new NoSuchElementException();
        Type element = first.next.element;
        first.next = first.next.next;
        return element;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Node<Type> temp = first.next; temp != null; temp = temp.next)
            s.append(temp.element);
        return s.toString();
    }
}
