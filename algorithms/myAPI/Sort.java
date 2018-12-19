package myAPI;

import java.util.Arrays;

public class Sort {

    public static void main(String[] args){
        Integer[] a = new Integer[20];
        String[] s = new String[20];
        for (int i = 0; i < a.length; i++){
            int t = (int)(Math.random()*100);
            a[i] = t;
            s[i] = t + "";
        }
        insert(a);
        insert(s);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(s));
    }

    public static void insert(Comparable[] a){
        for (int i = 0; i < a.length; i++)
            for (int j = i; j > 0; j--)
                if (cmp(a[j-1], a[j]) > 0)
                    exchange(j-1, j, a);
    }

    private static int cmp(Comparable a, Comparable b){
        return a.compareTo(b);
    }

    private static void exchange(int i, int j, Comparable[] a){
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
