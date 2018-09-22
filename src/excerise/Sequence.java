package excerise;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Sequence<Type> implements Cloneable, Iterable<Type>, Collection<Type>, List<Type> {

    private static final int BASE_CAPACITY = 10;
    private Type[] array;
    private int size;

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

    @Override
    public Type remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        if (size <= array.length/4)  resize(size/2);

        Type temp = array[index];
        for(int i = index; i < size-1; i++)
            array[i] = array[i+1];
        size--;
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

    @Override
    public ListIterator<Type> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Type> listIterator(int index) {
        return null;
    }

    @Override
    public List<Type> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Type type) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Type> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<Type> iterator() {
        return null;
    }
}
