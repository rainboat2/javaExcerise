package one;


public class PalindromicSubstring {

    public static void main(String[] args){
        PalindromicSubstring p = new PalindromicSubstring();
        String s = "aabaabbaa";
        String s1 = "a";
        System.out.println(p.longestPalindrome(s));
        System.out.println(p.longestPalindrome(s1));
    }

    /*
    思路：先寻找所有长度为1 和 2 的回文
    基于这些回文逐渐进行扩展
     */
    public String longestPalindrome(String s) {
        //int[]{begin, end}  前开后闭, end <= s.length

        String maxLength = "";
        for (int i = 0; i < s.length(); i++){
            //回文长度为奇数的情况
            String temp = findPalindromic(i, i, s);
            maxLength = (maxLength.length() >= temp.length()) ? maxLength : temp;

            //回文长度为偶数的情况
            if (i+1 != s.length() && s.charAt(i) == s.charAt(i+1)){
                temp = findPalindromic(i, i+1, s);
                maxLength = (maxLength.length() >= temp.length()) ? maxLength : temp;
            }
        }
        return maxLength;
    }


    /*
    但i = j 时，以 i 为中心来查找回文
    当i ！= j 时，以 i ，j 为中心来查找
     */
    public String findPalindromic(int i, int j, String s){
        int N = s.length();
        int begin = i, end = j;
        while (true){
            if (begin == 0 || end == N-1)  break;
            begin--;
            end++;
            if (s.charAt(begin) != s.charAt(end)){
                begin++;
                end--;
                break;
            }
        }
        return s.substring(begin, end+1);
    }
}
