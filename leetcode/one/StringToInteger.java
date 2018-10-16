package one;

import javax.security.sasl.SaslServer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class StringToInteger {

    public static void main(String[] args){
        StringToInteger s = new StringToInteger();
        String[] test = new String[]{"2147483648", "+-2"};
        System.out.println("2147483648");
        System.out.println(Integer.MAX_VALUE);
        for (int i = 0; i < test.length; i++)
            System.out.println(s.myAtom(test[i]));
    }

    public int myAtoi(String str) {

        Set<Character> set = new HashSet<>();
        set.addAll(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if        (set.contains(c))                                  builder.append(c);
            //开头的空格要跳过
            else if   (builder.length() == 0 && c == ' '){}
            //'+' 或 '-' 号只能放在开头位置
            else if   (builder.length() == 0 && (c == '+' || c =='-'))   builder.append(c);
            else                                                         break;
        }
        if (builder.length() == 0) return 0;
        try {
            return Integer.valueOf(builder.toString());
        }catch (NumberFormatException e){
            if (str.length() == 1)         return 0;
            else if (str.charAt(0) == '-') return Integer.MIN_VALUE;
            else                           return Integer.MAX_VALUE;
        }
    }

    public int myAtom(String str){
        str = str.trim();
        if (str.length() == 0)  return 0;
        int result = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i <= 9; i++)
            map.put(Character.forDigit(i, 10), i);
        int sign;
        if       (str.charAt(0) == '-')            sign = -1;
        else if  (str.charAt(0) == '+')            sign = 1;
        else if  (map.containsKey(str.charAt(0))){
            sign = 1;
            result = map.get(str.charAt(0));
        }
        else                                       return 0;


        for (int i = 1; i < str.length(); i++){
            Integer num = map.get(str.charAt(i));
            if (num == null)  break;
            int temp = result;
            result = result * 10 + num;
            int check = (result-num)/10;
            if (check != temp || check > result){
                if (sign == 1) return Integer.MAX_VALUE;
                else           return Integer.MIN_VALUE;
            }
        }
        return result * sign;
    }
}
