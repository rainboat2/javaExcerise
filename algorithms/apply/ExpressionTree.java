package apply;

import myAPI.CircularQueue;
import myAPI.NodeQueue;

import java.util.*;

public class ExpressionTree{

    public static void main(String[] args){
        ExpressionTree e = new ExpressionTree();
        Node tree = e.createTree("13-25/*2+");
        inOrder(tree);
        System.out.println();
        in(tree);
        System.out.println();
        preOrder(tree);
        System.out.println();
        pre(tree);
        System.out.println();
        postOrder(tree);
        System.out.println();
        post(tree);
        System.out.println();
        levelOrder(tree);
    }

    private static class Node{
        Character value;
        Node left;
        Node right;

        public Node(Character value, Node right, Node left){
            this.left = left;
            this.right = right;
            this.value = value;
        }
    }

    private static class StackNode{
        Node node;
        int popTimes;

        private StackNode(Node node){
            this.node = node;
        }
    }

    public Node createTree(String expression){
        char c;
        Stack<Node> stack = new Stack<>();
        Set<Character> set = new HashSet<>(Arrays.asList('+', '-', '*', '/'));
        for (int i = 0; i < expression.length(); i++){
            c = expression.charAt(i);
            if (set.contains(c))   stack.push(new Node(c, stack.pop(), stack.pop()));
            else                   stack.push(new Node(c, null, null));
        }
        return stack.pop();
    }

    public static void preOrder(Node node){
        if (node == null)  return;
        System.out.print(node.value);
        preOrder(node.left);
        preOrder(node.right);
    }

    public static void inOrder(Node node){
        if (node == null) return;
        System.out.print("(");
        inOrder(node.left);
        System.out.print(node.value);
        inOrder(node.right);
        System.out.print(")");
    }

    public static void postOrder(Node node){
        if (node == null)  return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value);
    }

    public static void in(Node node){
        Stack<Node> stack = new Stack<>();
        Node p = node;
        while (p != null || !stack.isEmpty()){
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
            p = stack.pop();
            System.out.print(p.value);
            p = p.right;
        }
    }

    public static void pre(Node node){
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        Node p;
        while(!stack.isEmpty()){
            p = stack.pop();
            if (p != null){
                System.out.print(p.value);
                //先进后出， 若要先变量左节点，必需要要后添加左节点
                stack.push(p.right);
                stack.push(p.left);
            }
        }
    }

    /**
     * 1.计数为 0，添加右子节点到栈中
     * 2.计数为 1，添加右子节点到栈中
     * 3.计数为 2，访问当前子节点
     * @param node
     */
    public static void post(Node node){
        Stack<StackNode> stack = new Stack<>();
        stack.push(new StackNode(node));
        StackNode cur;
        while (!stack.isEmpty()){
            cur = stack.peek();
            if (cur.node == null){
                stack.pop();
            }else if (cur.popTimes == 0){
                stack.push(new StackNode(cur.node.left));
                cur.popTimes++;
            }else if (cur.popTimes == 1){
                stack.push(new StackNode(cur.node.right));
                cur.popTimes++;
            }else {
                System.out.print(cur.node.value);
                stack.pop();
            }
        }
    }

    public static void levelOrder(Node node){
        NodeQueue<Node> queue = new NodeQueue<>();
        queue.enQueue(node);
        Node cur;
        while (!queue.isEmpty()){
            cur = queue.deQueue();
            if (cur != null){
                System.out.print(cur.value);
                queue.enQueue(cur.left);
                queue.enQueue(cur.right);
            }
        }
    }
}
