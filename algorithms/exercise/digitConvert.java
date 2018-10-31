package exercise;

public class digitConvert {

    static String digitTable = "0123456789abcdef";
    static final int MAX_BASE = digitTable.length();

    public static void main(String[] args){

        System.out.println(convert(64, 2));
        printIntRec(32, 16);
        System.out.println(convert(32, 16));
        printIntRec(30, 7);
    }

    public static void printIntRec(int n, int base){
        if (n >= base)
            printIntRec(n/base, base);
        System.out.print(digitTable.charAt(n%base));
    }

    public static String convert(int n, int base){
        if (n < base)
            return digitTable.charAt(n)+"";

        return convert(n/base, base)+""+ digitTable.charAt(n%base);
    }
}
