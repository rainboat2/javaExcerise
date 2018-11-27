package graph.digraph_search;

import graph.Digraph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DepthFirstOrder {

    public static void main(String[] args){
        DepthFirstOrder d = new DepthFirstOrder(Digraph.getDigraph());
        for (int i : d.pre)
            System.out.print(i + " ");
        System.out.println();
        for (int i : d.post)
            System.out.print(i + " ");
        System.out.println();
        Stack<Integer> s = d.reversePost();
        while (!s.isEmpty())
            System.out.print(s.pop() + " ");
    }

    private boolean[] marked;
    private Queue<Integer> pre;
    private Queue<Integer> post;
    private Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph g){
        pre = new LinkedList<>();
        post = new LinkedList<>();
        reversePost = new Stack<>();
        marked = new boolean[g.V()];
        for (int s = 0; s < g.V(); s++)
            if (!isMarked(s))
                dfs(g, s);
    }

    public void dfs(Digraph g, int v){
        marked[v] = true;
        pre.offer(v);
        for (int w : g.getAdj(v))
            if (!isMarked(w))
                dfs(g, w);
        post.offer(v);
        reversePost.push(v);
    }

    public boolean isMarked(int v){
        return marked[v];
    }

    public Iterable<Integer> pre(){
        return pre;
    }

    public Iterable<Integer> post(){
        return post;
    }

    public Stack<Integer> reversePost(){
        Stack<Integer> stack = new Stack<>();
        for (int i : reversePost)
            stack.push(i);
        return stack;
    }
}
