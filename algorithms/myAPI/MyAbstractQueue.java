package myAPI;

import java.util.Iterator;

public interface MyAbstractQueue<Item> extends Iterable<Item> {

    boolean isEmpty();

    int size();

    void add(Item item);

    Item poll();

    Item peek();

    Iterator<Item> iterator();
}