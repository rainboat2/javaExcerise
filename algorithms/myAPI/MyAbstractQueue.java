package myAPI;

public interface MyAbstractQueue<Item> {

    boolean isEmpty();

    int size();

    void add(Item item);

    Item poll();

    Item peek();
}