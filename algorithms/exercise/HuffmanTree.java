package exercise;

import myAPI.MinHeap;

import java.util.Map;

/*
 * 给出一个随机数组
 */
public class HuffmanTree<Type>{

    private Node root;

    private class Node implements Comparable<Node>{
        Type element;
        int frequent;
        Node left;
        Node right;

        public Node(Type element, int frequent){
            this.element = element;
            this.frequent = frequent;
        }

        public Node(Type element, int frequent, Node left, Node right){
            this(element, frequent);
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node o) {
            return frequent - o.frequent;
        }
    }

    private HuffmanTree(){}

    public void setTree(Map<Type, Integer> map){
        if (map.size() == 0){
            root = null;
            return;
        }
        MinHeap<Node> minHeap = new MinHeap<>();
        for (Type key : map.keySet())
            minHeap.add(new Node(key, map.get(key)));
        while (map.size() > 1){
            Node left = minHeap.getMin();
            Node right = minHeap.getMin();
            int total = left.frequent + right.frequent;
            minHeap.add(new Node(null, total, left, right));
        }
        root = minHeap.getMin();
    }
}
