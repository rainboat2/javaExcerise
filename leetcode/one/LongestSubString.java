package one;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubString {

    public static void main(String[] args){
        LongestSubString l = new LongestSubString();
        String s1 = "abcabcdef";
        String s2 = "bbbbbb";
        System.out.println(l.lengthOfLongestSubstring(s2));
    }

    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int begin = 0;
        int end;
        for (end = 1; end < s.length(); end++){
            char c = s.charAt(end);
            //寻找目前的子串中是否有和c重复的字符
            for (int i = begin; i < end; i++){
                if (s.charAt(i) == c){
                    maxLength = Math.max(maxLength, end - begin);
                    begin = i + 1;
                    break;
                }
            }
        }
        if (s.length() - begin > maxLength)  maxLength = s.length() - begin;
        return maxLength;
    }

    /**
     * @param s
     * @return int
     *
     * 更好的方法，来源leetcode解答
     */
    public int lengthOfLongestSubstring1(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    public int lengthOfLongestSubstring2(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
}
