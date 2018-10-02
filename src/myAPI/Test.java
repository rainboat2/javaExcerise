package myAPI;

import java.util.*;

public class Test<Type> {

    public static void main(String[] args){
        LinkedList<Integer> list = new LinkedList<>();
        for(int i = 0; i < 15; i++)
            list.add(i);
        ListIterator<Integer> it = list.listIterator();
        System.out.println(it.previousIndex());
        System.out.println(it.nextIndex());
    }

}
