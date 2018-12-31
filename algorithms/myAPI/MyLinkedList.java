package myAPI;

import java.util.*;

public class MyLinkedList<Type> implements List<Type>, Cloneable {

    private Node<Type> head;
    private Node<Type> tail;
    private int size;
    private int modTimes;    //记录被修改的次数


    /**
     * head只需要next， 而tail只需要pre，故两者可以共用一个tail类
     * head和tail尽管是两个不同的引用，但由于其指向一个对象，两者的值是一样的
     * 本链表的本质是一个循环链表
     */
    public MyLinkedList(){
        clear();
    }

    private class Node<Type>{

        Type element;
        Node<Type> next;
        Node<Type> pre;

        public Node(){}
        public Node(Type element, Node<Type> next, Node<Type> pre){
            this.element = element;
            this.next = next;
            this.pre = pre;
        }

        public String toString(){
            return element+"";
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Node<Type> temp = head.next; temp != tail; temp = temp.next){
            if (temp.equals(o))  return true;
        }
        return false;
    }

    private class MyIterator implements Iterator<Type>{

        private Node<Type> current = head.next;
        private int mod = modTimes;

        @Override
        public boolean hasNext() {
            if (modTimes != mod)
                throw new ConcurrentModificationException();
            return current != tail;
        }

        @Override
        public Type next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Type value = current.element;
            current = current.next;
            return value;
        }

        public void remove(){
            MyLinkedList.this.remove(current);
            mod++;
        }
    }

    @Override
    public Iterator<Type> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<Type> temp = head;
        for (int i = 0; i < size; i++){
            temp = temp.next;
            array[i] = temp.element;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Object[] array = toArray();
        if (a.length < size)
            a = (T[]) array;
        else{
            for (int i = 0; i < size; i++)
                a[i] = (T) array[i];
        }
        return a;
    }

    @Override
    public boolean add(Type type) {
        add(size, type);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (Node<Type> temp = head.next; temp.next != tail; temp = temp.next){
            if (temp.element.equals(o)){
                temp.pre.next = temp.next;
                temp.next.pre = temp.pre;
                size--;
                modTimes++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object temp : c){
            if (!contains(temp))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Type> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Type> c) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        //找到插入位置前面和后面的两个节点
        Node<Type> after = getNode(index);
        Node<Type> before = after.pre;

        //构造一个双向链表，方便后面插入
        Node<Type> head = new Node<>();
        Node<Type> tail = new Node<>();
        head.next = tail;
        tail.pre = head;
        Iterator it = c.iterator();
        while (it.hasNext()){
            Node<Type> newNode = new Node<>((Type)it.next(), tail, tail.pre);
            newNode.pre.next = newNode;
            tail.pre = newNode;
        }

        //开始插入整个链表
        before.next = head.next;
        head.next.pre = before;

        tail.pre.next = after;
        after.pre = tail.pre;
        size += c.size();
        modTimes++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;
        Iterator it = c.iterator();
        while (it.hasNext()){
            flag = remove(it.next()) || flag;
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;
        for (Node<Type> temp = head.next; temp != tail; temp = temp.next){
            if (!c.contains(temp)){
                temp.pre.next = temp.next;
                temp.next.pre = temp.pre;
                flag = true;
                size--;
                modTimes++;
            }
        }
        return flag;
    }

    @Override
    public void clear() {
        head = new Node<>();
        tail = head;
        head.next = tail;
        tail.pre = head;
        size = 0;
    }

    private Node<Type> getNode(int index){
        //当index在靠前的位置时，从前往后遍历，靠后时，则从后往前遍历
        Node<Type> temp;
        if ((size/2) > index){
            temp = head;
            for(int i = -1; i < index; i++)
                temp = temp.next;
        }else{
            temp = tail;
            for(int i = size; i > index; i--)
                temp = temp.pre;
        }
        return temp;
    }

    @Override
    public Type get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return this.getNode(index).element;
    }

    @Override
    public Type set(int index, Type element) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<Type> temp = getNode(index);
        Type elem = temp.element;
        temp.element = element;
        modTimes++;
        return elem;
    }

    @Override
    public void add(int index, Type element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        Node<Type> temp = getNode(index);
        Node<Type> newNode = new Node<>();
        newNode.element = element;
        //开始将新的节点放在temp的前面
        newNode.pre = temp.pre;
        temp.pre.next = newNode;
        temp.pre = newNode;
        newNode.next = temp;
        size++;
        modTimes++;
    }

    @Override
    public Type remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<Type> temp = getNode(index-1);   //获得要被删除的节点的前一个节点
        Type elem = temp.next.element;

        //开始删除指定节点
        temp.next = temp.next.next;
        temp.next.pre = temp;
        size--;
        modTimes++;
        return elem;
    }

    @Override
    public int indexOf(Object o) {
        Node<Type> temp = head;
        for (int i = 0; i < size; i++){
            temp = temp.next;
            if (temp.element.equals(0)){
                return i;
            }
        }
        return -2;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<Type> temp = tail;
        for (int i = size-1; i >= 0; i++){
            temp = temp.pre;
            if (temp.element.equals(o)){
                return i;
            }
        }
        return -2;
    }

    private class MyListIterator implements ListIterator<Type>{

        private Node<Type> current;
        private int mod = MyLinkedList.this.modTimes;
        private int index;

        private MyListIterator() {current = head.next; }

        private MyListIterator(int index) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException();
            current = getNode(index);
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            if (modTimes != mod)
                throw new ConcurrentModificationException();
            return index < size;
        }

        @Override
        public Type next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Type value = current.element;
            current = current.next;
            index++;
            return value;
        }

        @Override
        public boolean hasPrevious() {
            if (modTimes != mod)
                throw new ConcurrentModificationException();
            return index > 0;
        }

        @Override
        public Type previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            Type value = current.pre.element;
            current = current.pre;
            index--;
            return value;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            current = current.pre;
            mod++;
            MyLinkedList.this.remove(this.index--);
        }

        @Override
        public void set(Type type) {
            if (modTimes != mod)
                throw new ConcurrentModificationException();
            mod++;
            current.element = type;
        }

        @Override
        public void add(Type type) {
            if (modTimes != mod)
                throw new ConcurrentModificationException();
            mod++;
            MyLinkedList.this.add(index, type);
        }
    }

    @Override
    public ListIterator<Type> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<Type> listIterator(int index) {
        return new MyListIterator(index);
    }

    @Override
    public List<Type> subList(int fromIndex, int toIndex) {
        MyLinkedList<Type> list = new MyLinkedList<>();
        Node<Type> temp = getNode(fromIndex);
        for (int i = 0; i < toIndex; i++){
            list.add(temp.element);
            temp = temp.next;
        }
        return list;
    }

    @Override
    public String toString(){
        String s = "";
        for (Node<Type> temp = head.next; temp != tail; temp = temp.next){
            s += temp.element + " ";
        }
        return s;
    }

    @Override
    public MyLinkedList<Type> clone(){
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        MyLinkedList<Type> linkedList = new MyLinkedList<>();
        linkedList.addAll(this);
        return linkedList;
    }
}
