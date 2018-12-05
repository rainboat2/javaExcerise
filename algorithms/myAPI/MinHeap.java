package myAPI;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

public class MinHeap<Type extends Comparable<Type>> {

    public static void main(String[] args){
        MinHeap<Integer> minHeap = new MinHeap<>();
        Random random = new Random();
        for (int j = 0; j < 3; j++){
            for (int i = 0; i < 20; i++){
                minHeap.add(random.nextInt(100));
            }
            System.out.println(minHeap.size +" " + minHeap);
            for (int i = 0; i < 20; i++){
                System.out.print(minHeap.getMin()+" ");
            }
            System.out.println();
            System.out.println(minHeap.size +" " + minHeap);
        }
        Integer[] a = new Integer[35];
        for (int i = 0; i < a.length; i++)
            a[i] = random.nextInt(100);
        minHeap = new MinHeap<>(a);
        for (int i = 0; i < a.length; i++)
            System.out.print(minHeap.getMin() + " ");
    }

    /*
     * 第一个节点索引 ： 0
     * 最后一个节点索引: size-1
     * 子节点：2n+1, 2n+2
     * 父节点: (n-1)/2
     */
    private Type[] elements;
    private int size;

    public MinHeap()         { this(10);}
    public MinHeap(int size) { elements = (Type[]) new Comparable[size];}
    public MinHeap(Type[] array){
        elements = array;
        size = array.length;
        for (int i = (size/2 -1); i >= 0; i--)
            sink(i);
    }

    public boolean isEmpty(){ return size == 0;}

    public boolean contains(Type x){
        return indexOf(x) != -1;
    }

    public int indexOf(Type x){
        if (x == null) return -1;
        for (int i = 0; i < size; i++)
            if (elements[i].equals(x))
                return i;
        return -1;
    }

    public void add(Type ele){
        if (size == elements.length) resize(size*2);
        elements[size++] = ele;
        swim(size-1);
    }

    public Type peek(){
        if (isEmpty())
            throw new NoSuchElementException();
        return elements[0];
    }

    public Type getMin(){
        Type result = elements[0];
        exchange(0, --size);
        sink(0);
        elements[size] = null;
        return result;
    }

    private void resize(int newSize){
        Type[] temp = (Type[]) new Comparable[newSize];
        System.arraycopy(elements, 0, temp, 0, elements.length);
        elements = temp;
    }

    /*
     * 将较大的节点下沉
     * 若当前节点大于子节点中任意一个，与子节点中最小的一个进行替换
     */
    private void sink(int i){
        while ((2*i+1) <= size-1){
            int j = 2*i+1;
            if (j < size-1 && compare(j, j+1) > 0) j++;
            if (compare(i, j) < 0)                 break;
            exchange(i, j);
            i = j;
        }
    }

    /*
     * 将较小的节点上浮
     * 若当前节点小于其父节点，将其与父节点交换
     */
    private void swim(int i){
        while(i != 0){
            int j = (i - 1) / 2;
            if (compare(i, j) > 0)  break;
            exchange(i, j);
            i = j;
        }
    }

    private int compare(int i, int j){
        return elements[i].compareTo(elements[j]);
    }

    private void exchange(int i, int j){
        Type temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    public String toString(){
        return Arrays.toString(elements);
    }
}
