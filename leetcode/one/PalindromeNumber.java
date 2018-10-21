package one;

public class PalindromeNumber {

    public static void main(String[] args){
        PalindromeNumber p = new PalindromeNumber();
        int[] nums = new int[]{456, 123321, 0, 1001, 89};
        for (int i = 0; i < nums.length; i++)
            System.out.println(nums[i] + "  " + p.isPalindrome(nums[i]));
    }

    public boolean isPalindrome(int x) {
        if (x < 0)   return false;
        int length = getLength(x);
        int[] array = new int[length];
        for (int i = 0; i < length; i++){
            array[i] = x%10;
            x = x / 10;
        }
        for (int i = 0; i < length/2; i++)
            if (array[i] != array[length-i-1])
                return false;
        return true;
    }

    public int getLength(int x){
        int temp = 0;
        while (x != 0){
            temp++;
            x = x / 10;
        }
        return temp;
    }
}
