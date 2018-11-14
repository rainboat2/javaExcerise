package myAPI;

public class RedBlackBST<Key extends Comparable<Key>, Value>{

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node{
        Key key;
        Value value;
        Node left, right;
        int N;
        boolean color;

        public Node(Key key, Value value, int N, boolean color){
            this.key = key;
            this.value = value;
            this.N = N;           // 该子树中的结点总数
            this.color = color;   // 由父节点指向该节点的链接颜色
        }
    }

    private int size(Node x){
        if (x == null) return 0;
        return size(x.left) + size(x.right) + 1;
    }

    private boolean isRed(Node x){
        if (x == null) return false;
        return x.color == RED;
    }

    // 将当前在右边的红节点向左边旋转
    private Node rotateLeft(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h);
        return x;
    }

    // 将当前在左边的红节点旋转到右边
    private Node rotateRight(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h);
        return x;
    }

    private void filpColors(Node h){
        h.left.color = BLACK;
        h.right.color = BLACK;
        h.color = RED;
    }

    public void put(Key key, Value value){
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value value){
        if (h == null)  return new Node(key, value, 1, RED);

        int cmp = h.key.compareTo(key);
        if      (cmp > 0)  h.left = put(h.left, key, value);
        else if (cmp < 0)  h.right = put(h.right, key, value);
        else               h.value = value;

        if (isRed(h.right) && !isRed(h.left))    h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     filpColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }
}
