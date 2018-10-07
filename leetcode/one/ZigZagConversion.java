package one;

import java.util.Arrays;

public class ZigZagConversion {

    public static void main(String[] args){
        ZigZagConversion z = new ZigZagConversion();
        System.out.println(Arrays.toString(z.createPositionArray(10, 8)));
        System.out.println(z.convert("PAYPALISHIRING", 4));
    }

    /*
     * 为每一行都建立一个string builder，将每一行的字符都添加到对应的builder中
     * 到最后将每一个builder中的字符按顺序连接到一起
     */
    public String convert(String s, int numRows) {
        int[] position = createPositionArray(s.length(), numRows);
        StringBuilder[] rowBuilders = new StringBuilder[numRows];
        // 为每一行建立都建立一个builder
        for (int i = 0; i < numRows; i++)
            rowBuilders[i] = new StringBuilder();

        for (int i = 0; i < s.length(); i++)
            rowBuilders[position[i]].append(s.charAt(i));

        StringBuilder result = new StringBuilder();
        for (StringBuilder temp : rowBuilders)
            result.append(temp.toString());
        return result.toString();
    }

    public int[] createPositionArray(int length, int numRows){
        int[] array = new int[length];
        int i = 0;
        while (i < length){
            for (int num = 0; num < numRows && i < length; num++)
                array[i++] = num;

            for (int num = numRows-2; num >= 1 && i < length; num--)
                array[i++] = num;
        }
        return array;
    }
}
