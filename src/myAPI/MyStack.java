package myAPI;

import java.util.NoSuchElementException;

public class MyStack<Type> {

    private Node<Type> first;
    private int N;

    private class Node<Type>{
        Node next;
        Type element;

        public Node(Type element, Node next){
            this.element = element;
            this.next = next;
        }
    }

    public boolean isEmpty()   { return N == 0;}
    public int size()          { return N;}

    public void push(Type element){
        Node<Type> newNode = new Node<>(element, first);
        first = newNode;
        N++;
    }

    public Type pop(){
        if (isEmpty())
            throw new NoSuchElementException();
        Type element = first.element;
        first = first.next;
        N--;
        return element;
    }

    public Type getTop(){
        return first.element;
    }
}
