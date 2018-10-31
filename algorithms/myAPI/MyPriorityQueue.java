package myAPI;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyPriorityQueue<Type extends Comparable<Type>>{

    public static void main(String[] args){
        MyPriorityQueue<Integer> priorityQueue = new MyPriorityQueue<>();
        for (int j = 0; j < 3; j++){
            for (int i = 0; i < 20; i++){
                priorityQueue.add((int)(Math.random()*100));
            }
            System.out.println(priorityQueue);
            for (int i = 0; i < 20; i++){
                System.out.print(priorityQueue.getMax()+" ");
            }
            System.out.println();
            System.out.println(priorityQueue);
        }
    }

    /*
     * array 中存放的为一个二叉堆
     * 对于其中任意一个节点i，有以下特点：
     * 1. i的父节点： i/2
     * 2. i的左子节点： i*2
     * 3. i的右子节点： i*2+1
     * 4. i的优先级必然大于或等于其两个子节点
     * size 表示队列中元素的个数，由于此队列第一个为空，故size可以当作最后一个元素的索引
     */
    private Type[] array;
    private int size;

    public MyPriorityQueue()         { this(10);}
    public boolean isEmpty()         { return size == 0;}

    public MyPriorityQueue(int size) {
        array = (Type[]) new Comparable[size+1];
    }

    private void resize(int newSize){
        Type[] temp = (Type[])new Comparable[newSize];
        System.arraycopy(array, 0, temp, 0, array.length);
        array = temp;
    }

    /*
     * 给定一个节点，对该节点进行上浮的操作
     * 具体操作：
     *     当该节点优先级大于其父节点，则将其与父节点交换
     *     否则结束操作
     */
    private void swim(int i){
        while (i/2 > 0){
            int j = i/2;
            if (array[i].compareTo(array[j]) <= 0) break;
            exchange(i, j);
            i = j;
        }
    }

    /*
     * 给定一个节点，对其进行下沉的操作
     * 当当前节点的优先级大于其两个子节点，结束
     * 否则与两个子节点中最大的进行交换
     */
    private void sink(int i){
        while (i*2 < size){
            int j = i*2;    //j为i的左子节点
            if (j+1 < size && array[j].compareTo(array[j+1]) < 0)   j++;   //将j设为两个子节点中最小的一个
            int cmp = array[i].compareTo(array[j]);
            if (cmp >= 0)  break;
            exchange(i, j);
            i = j;     //将当前节点下移到其子节点
        }
    }

    public Type getMax(){
        if (isEmpty())
            throw new NoSuchElementException();
        Type max = array[1];
        exchange(1, size);
        array[size--] = null;
        sink(1);
        return max;
    }

    public void add(Type value){
        if (array.length <= size+1) resize(array.length*2);
        array[++size] = value;
        swim(size);
    }

    private void exchange(int i, int j){
        Type temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public String toString(){
        return Arrays.toString(array);
    }
}