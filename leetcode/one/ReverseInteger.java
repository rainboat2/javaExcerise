package one;

public class ReverseInteger {

    public static void main(String[] args){
        ReverseInteger r = new ReverseInteger();
        System.out.println(r.reverse(-1234));
        System.out.println(r.reverse(12345));
        System.out.println(r.reverse(1534236469));
        System.out.println(r.reverse(-2147483648));
    }

    public int reverse(int x) {
        int result = 0;
        while(x != 0){
            int temp = result*10;
            if (temp /10 != result)  return 0;
            result = result*10 + x%10;
            x = x/10;
        }
        return result;
    }
}
