package one;

public class RegularExpression {

    public static void main(String[] args){
        RegularExpression r = new RegularExpression();
        System.out.println(r.isMatch("ab", ".*c"));
        System.out.println(r.isMatch("", ".*"));
    }

    /**
     * 递归解决
     * 终止条件：p为空
     * 递推： 匹配第一项
     *       结果为 （s、p）第一次匹配结果 + （s[1:]、p)|| （s[1:], p[1:]) 匹配的结果
     *       情况(s[1:],p) : p 第一个为 任意字符， 第二个为*
     *       情况（s[1:],p[1:]) : 第一项匹配结束，继续匹配第二项
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        if (p.isEmpty())  return s.isEmpty();
        boolean firstMatch = !s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
        if (p.length() >= 2 && p.charAt(1) == '*')
            //     跳过通配符（匹配0次）             对通配符进行一次匹配
            return (isMatch(s, p.substring(2))) || (firstMatch && isMatch(s.substring(1), p));
        else
            //     无通配符的情况
            return firstMatch && isMatch(s.substring(1), p.substring(1));
    }

    /*
     * 简单情况下的递归匹配（没有通配符’*‘）
     */
    public boolean match(String s, String p){
        if (p.isEmpty())  return s.isEmpty();
        boolean fistMatch = (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
        return fistMatch && match(s.substring(1), p.substring(1));
    }
}
