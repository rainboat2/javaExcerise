package excerise;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SequenceList_1<Type> implements Cloneable, Iterable<Type> {

    private static final int BASE_CAPACITY = 10;
    private Type[] array;
    private int size;

    public SequenceList_1() {
        this(BASE_CAPACITY);
    }

    public SequenceList_1(int size) {
        array = (Type[]) new Object[size];
    }

    public SequenceList_1(Type[] a) {
        this(a.length);
        System.arraycopy(a, 0, array, 0, a.length);
        size = a.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }


    public void clearAll() {
        array = (Type[]) new Object[BASE_CAPACITY];
        size = 0;
    }

    public Type get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return array[index];
    }

    public void deleteLast() {
        //当元素总数不及数组长度的二分之一时，缩小数组以减少内存占用
        if (size <= array.length / 4)
            resize(array.length / 2);

        array[size - 1] = null;  //释放对象，减少堆空间的占用
        size--;
    }

    public void set(int index, Type item) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        array[index] = item;
    }

    public void remove(Type item) {
        for (int i = 0; i < size; i++) {
            if (item.equals(array[i])) {
                remove(i);
                return;
            }
        }
    }

    public void remove(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        //下面先循环将移除元素后的所有元素前移一位，再删除最后一个重复的元素
        for (int i = index; i < size - 1; i++)
            array[i] = array[i + 1];
        deleteLast();
    }

    public void insert(int index, Type item) {
        if (size == array.length)
            resize(array.length * 2);

        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        //下面循环将插入位置后的所有元素都后移一位
        for (int i = size; i > index; i--)
            array[i] = array[i - 1];
        array[index] = item;
        size++;
    }

    public void add(Type item) {
        if (size == array.length) resize(array.length * 2);
        array[size] = item;
        size++;
    }

    public void addAll(SequenceList_1<Type> a) {
        for (int i = 0; i < a.size(); i++) {
            add(a.get(i));
        }
    }

    private void resize(int length) {
        Type[] temp = (Type[]) new Object[length];
        System.arraycopy(array, 0, temp, 0, size);
        array = temp;
    }

    public int indexOf(Type item) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(item)) return i;
        }
        return -1;
    }

    public SequenceList_1<Type> clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        SequenceList_1<Type> temp = new SequenceList_1<>(size);
        System.arraycopy(array, 0, temp.array, 0, size);
        temp.size = this.size;
        return temp;
    }

    public String toString() {
        String s = "";
        for (Type temp : array) {
            if (temp != null) s += " " + temp;
            else break;
        }
        return s;
    }

    private class ArrayListIterator implements Iterator<Type> {

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
            SequenceList_1.this.remove(--current);
        }
    }

    @Override
    public Iterator<Type> iterator() {
        return new ArrayListIterator();
    }
}
