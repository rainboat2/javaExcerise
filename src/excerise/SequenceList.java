package excerise;

import java.util.*;

public class SequenceList<Type> implements Cloneable, Iterable<Type>, Collection<Type>, List<Type> {

    private static final int BASE_CAPACITY = 10;
    private Type[] array;
    private int size;

    public SequenceList() { this(BASE_CAPACITY);}

    public SequenceList(int capacity){
        array = (Type[]) new Object[capacity];
    }

    private void resize(int newSize){
        Type[] newArray = (Type[]) new Object[newSize];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Type> c) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        int N = c.size();
        if (size + c.size() >= array.length)
            resize(size*2 + N);

        for(int i = size+N-1; i >= index+N; i--)
            array[i] = array[i-N];                      //将插入地方后的所有元素移动插入数组长度的距离

        for (Type item: c){
            array[index++] = item;                     //将空缺部分填补
        }
        size += N;
        //TODO: 重点测试
        return true;
    }

    @Override
    public Type get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return array[index];
    }

    @Override
    public Type set(int index, Type element) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        Type pre = array[index];                        //记录之前在这个位置的元素
        array[index] = element;
        return pre;
    }

    @Override
    public void add(int index, Type element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        if (size >= array.length)  resize(2*size);
        //将元素向后移动
        for(int i = size; i > index; i--)
            array[i] = array[i-1];
        array[index] = element;
        size++;
    }

    public static void main(String[] args){
        SequenceList<Integer> t = new SequenceList<>();
        for(int i = 0; i < 10; i++)
            t.add(i+1);
        t.set(9, 0);
        System.out.println(t);
        t.remove((Integer)0);
        System.out.println(t);
    }

    @Override
    public Type remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        if (array.length > 16 && size <= array.length/4)  resize(size/2);

        Type temp = array[index];
        for(int i = index; i < size-1; i++){
            array[i] = array[i+1];
        }
        array[--size] = null;                               //释放该处的对象，减少空间的占用
        return temp;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++){
            if (array[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size -1; i >= 0; i--){
            if (array[i].equals(o))
                return i;
        }
        return -1;
    }

    //ListIterator的类
    private class MyListIterator implements ListIterator<Type>{

        private int current;

        public MyListIterator()           { current = 0;}
        public MyListIterator(int index)  { current = index;}

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public Type next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return array[current++];
        }

        @Override
        public boolean hasPrevious() {
            return current != 0;
        }

        @Override
        public Type previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            return array[--current];
        }

        @Override
        public int nextIndex() {
            if (!hasNext())
                throw new NoSuchElementException();
            return current + 1;
        }

        @Override
        public int previousIndex() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            return current -1;
        }

        @Override
        public void remove() {
            SequenceList.this.remove(--current);
        }

        @Override
        public void set(Type o) {
            SequenceList.this.set(current, o);
        }

        @Override
        public void add(Type o) {
            SequenceList.this.add(current++, o);
        }
    }

    @Override
    public ListIterator<Type> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<Type> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        return new MyListIterator(index);
    }

    @Override
    public List<Type> subList(int fromIndex, int toIndex) {
        SequenceList<Type> sub = new SequenceList<>(toIndex - fromIndex);
        for(int i = fromIndex; i <= toIndex; i++)
            sub.add(array[i]);
        return sub;
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
        return indexOf(o) != -1;

    }

    @Override
    public Object[] toArray() {
        Object[] temp = new Object[size];
        System.arraycopy(array, 0, temp, 0, size);
        return temp;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[])new Object[size];

        for(int i = 0; i < size; i++)
            a[i] = (T) array[i];
        return a;
    }

    @Override
    public boolean add(Type type) {
        add(size, type);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c){
            if (indexOf(item) == -1)
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Type> c) {
        return addAll(size, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object item : c){
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Type[] newArray = (Type[]) new Object[size];
        int index = 0;
        for(int i = 0; i < size; i++){
            for(Object temp : c){
                //找出所有在c中的元素并将其放在newArray中
                if (array[i].equals(temp)){
                    newArray[index++] = array[i];
                    break;
                }
            }
        }
        array = newArray;
        size = index;
        return true;
    }

    @Override
    public void clear() {
        array = (Type[]) new Object[BASE_CAPACITY];
        size = 0;
    }

    private class MyIterator implements Iterator<Type> {

        private int current;

        @Override
        public boolean hasNext() {
            return current < size();
        }

        @Override
        public Type next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return array[current++];
        }

        @Override
        public void remove() {
            SequenceList.this.remove(--current);
        }
    }

    @Override
    public Iterator<Type> iterator() {
        return new MyIterator();
    }

    @Override
    public SequenceList<Type> clone(){
        try {
            super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }

        Type[] newArray = (Type[]) new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        SequenceList<Type> sl = new SequenceList<>();
        sl.array = newArray;
        sl.size = this.size;
        return sl;
    }

    @Override
    public String toString() {
        String s = "";
        for (Type temp : array) {
            if (temp != null) s += " " + temp;
            else break;
        }
        return s;
    }
}
