package rail.myAPI;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

@SuppressWarnings("Duplicates")
public class BinarySearchTree<Key extends Comparable<Key>, Value> implements Iterable<Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public String toString(){
            return this.key + "";
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return size(x.left) + size(x.right) + 1;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = x.key.compareTo(key);
        if (cmp < 0) return get(x.right, key);
        else if (cmp > 0) return get(x.left, key);
        else return x.value;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) return new Node(key, value);
        int cmp = x.key.compareTo(key);
        if      (cmp < 0) x.right = put(x.right, key, value);
        else if (cmp > 0) x.left = put(x.left, key, value);
        else x.value = value;
        return x;
    }


    public Key select(int rank){
        Node node = select(root, rank);
        if   (node == null)   return null;
        else                  return node.key;
    }

    private Node select(Node node, int rank){
        if (node == null) return null;
        int leftSize = size(node.left);
        if       (leftSize > rank)  return select(node.left, rank);
        else if  (leftSize < rank)  return select(node.right, rank - leftSize - 1);
        else                        return node;
    }

    public int rank(Key key){
        return rank(root, key);
    }

    //返回key在node树中的节点
    private int rank(Node node, Key key){
        if (node == null)  return 0;
        int cmp = node.key.compareTo(key);
        if      (cmp == 0)    return size(node.left);
        else if (cmp > 0)     return rank(node.left, key);
        else                  return size(node.left) + 1 + rank(node.right, key);
    }

    public Iterator<Value> iterator() {
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<Value>{

        private Stack<Node> stack = new Stack<>();
        private Node current = root;

        public boolean hasNext() {
            return current != null || !stack.isEmpty();
        }

        public Value next() {
            if (!hasNext())
                throw new NoSuchElementException();
            while (current != null){
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            Node rs = current;
            current = current.right;
            return rs.value;
        }
    }
}
