package apply;

import myAPI.MyStack;
import myAPI.SequenceList;

import java.util.Arrays;
import java.util.HashMap;

public class PostorderExpression {

    public static void main(String[] args){
        String expression = "((1-2)*(2/5))*2";
        System.out.println(convert(expression));
        System.out.println(count(convert(expression)));
    }

    private static HashMap<Character, Integer> priority = new HashMap<>();

    static {
        //定义基本操作符的优先级
        priority.put('+', 5);
        priority.put('-', 5);
        priority.put('*', 10);
        priority.put('/', 10);
    }

    public static double count(String expression){
        MyStack<Double> stack = new MyStack<>();
        for (int i = 0; i < expression.length(); i++){
            char s = expression.charAt(i);
            if       (s == '+')      stack.push(stack.pop() + stack.pop());
            else if  (s == '*')      stack.push(stack.pop() * stack.pop());
            else if  (s == '-'){
                double right = stack.pop();
                stack.push(stack.pop() - right);
            }else if (s == '/'){
                double right = stack.pop();
                stack.push(stack.pop() / right);
            }else                    stack.push(Double.parseDouble(String.valueOf(s)));
        }
        return stack.pop();
    }

    public static boolean check(String expression){
        MyStack<Character> stack = new MyStack<>();
        char s;
        for (int i = 0; i < expression.length(); i++){
            s = expression.charAt(i);
            if (s == '[' || s == '(' || s == '{')      stack.push(s);
            else if (s == ']' || s == ')' || s == '}'){
                if (s != stack.pop())  return false;
            }
        }

        if (stack.isEmpty()) return true;
        else                 return false;
    }

    public static String convert(String expression){
        StringBuilder result = new StringBuilder();
        MyStack<Character> stack = new MyStack<>();

        //以下两行将涉及到的运算符放入到list中，方便后续比较操作
        SequenceList<Character> operatorList = new SequenceList<>(6);
        operatorList.addAll(Arrays.asList('(', ')', '+', '-', '*', '/'));

        char s;
        for (int i = 0; i < expression.length(); i++){
            s = expression.charAt(i);
            if (!operatorList.contains(s))    result.append(String.valueOf(s));
            else                              solveOperator(s, result, stack);
        }
        while (!stack.isEmpty())   result.append(stack.pop());
        return result.toString();
    }

    /**
     * 如果待处理的操作符为‘)', 一直弹出stack直到弹出到’（‘为止
     * 如果为其他普通的+ - * /， 弹出栈中所有优先级大于等于当前优先级的运算符，再将当前运算符压入栈中
     *
     * @param s 待处理的运算符
     * @param result  作为结果的字符串
     * @param stack   保存运算符的栈
     *
     */
    private static void solveOperator(char s, StringBuilder result, MyStack<Character> stack){
        if (s == ')'){
            while (stack.getTop() != '(')
                result.append(stack.pop());
            stack.pop();  //删除栈中的左括号
            return;
        }
        //处理普通情况
        while (!stack.isEmpty() && priorityInStack(stack.getTop()) >= priorityOutStack(s))
            result.append(stack.pop());
        stack.push(s);
    }

    public static int priorityInStack(char a){
        priority.put('(', Integer.MIN_VALUE);     //在stack中‘（’的优先级最低
        return priority.get(a);
    }

    public static int priorityOutStack(char a){
        priority.put('(', Integer.MAX_VALUE);     //在stack外‘（‘具有最高的优先级
        return  priority.get(a);
    }
}
