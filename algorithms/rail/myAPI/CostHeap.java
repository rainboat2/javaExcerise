package rail.myAPI;

import rail.City;

import java.util.NoSuchElementException;

public class CostHeap {

    private Node[] heap;
    private int size;

    private class Node implements Comparable<Node> {
        City name;
        int cost;

        public Node(City name, int cost){
            this.name = name;
            this.cost = cost;
        }

        public int compareTo(Node that){
            return Integer.compare(this.cost, that.cost);
        }
    }

    public CostHeap(){
        this(10);
    }

    @SuppressWarnings("unchecked")
    public CostHeap(int size){
        heap = new Node[10];
    }


    public boolean isEmpty() { return size == 0;}

    public void add(City name, int cost){
        Node n = new Node(name, cost);
        if (size == heap.length) resize(size*2);
        heap[size++] = n;
        swim(size-1);
    }

    public City peek(){
        if (isEmpty())
            throw new NoSuchElementException();
        return heap[0].name;
    }

    public City getMin(){
        Node result = heap[0];
        exchange(0, --size);
        sink(0);
        heap[size] = null;
        return result.name;
    }

    public boolean contains(City name){
        return find(name) != -1;
    }

    public int find(City name){
        for (int i = 0; i < size; i++)
            if (heap[i].name.equals(name))
                return i;
        return -1;
    }

    public void change(City name, int cost){
        int index = find(name);
        if (index == -1) return;
        int before = heap[index].cost;
        heap[index].cost = cost;
        if (before < cost)   sink(index);
        else                   swim(index);
    }

    private void resize(int newSize){
        Node[] temp = new Node[newSize];
        System.arraycopy(heap, 0, temp, 0, heap.length);
        heap = temp;
    }

    /*
     * 将较大的节点下沉
     * 若当前节点大于子节点中任意一个，与子节点中最小的一个进行替换
     */
    private void sink(int i){
        while ((2*i+1) <= size-1){
            int j = 2*i+1;
            if (j < size-1 && cmp(j, j+1) > 0)  j++;
            if (cmp(i, j) < 0)                    break;
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
            if (cmp(i, j) > 0)  break;
            exchange(i, j);
            i = j;
        }
    }

    private void exchange(int i, int j){
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int cmp(int i, int j){
        return heap[i].compareTo(heap[j]);
    }
}
