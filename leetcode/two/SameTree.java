package two;

import java.util.List;
import java.util.Stack;

public class SameTree {


    // Definition for a binary tree node.
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /*
     * p q 值不相等
     * p为null而q不为null,或相反
     *
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)  return true;    //全为null的情况
        if (p == null || q == null)  return false;   //只有一个为null

        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

//    public List<List<Integer>> levelOrderBottom(TreeNode root) {
//        Stack<TreeNode> stack = new Stack<>();
//        stack.push(root);
//        TreeNode cur;
//        while (cur != null && !stack.isEmpty()){
//            stack.push(cur);
//            cur = cur.left;
//        }
//    }
}
