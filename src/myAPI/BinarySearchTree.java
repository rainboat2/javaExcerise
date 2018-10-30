package myAPI;

class QueueTest{
    public static void main(String[] args){
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        for (int i = 0; i < 20; i++){
            int a = (int)(Math.random()*50);
            tree.put(a, a);
        }
        System.out.println(tree.inOrder());
        System.out.println(tree.size());
        System.out.println(tree.select(0));
        System.out.println(tree.select(18));

    }
}

public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int N;

        public Node(Key key, Value value, int N) {
            this.key = key;
            this.value = value;
            this.N = N;
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
        if (x == null) return x.value;
        int cmp = x.key.compareTo(key);
        if (cmp < 0) return get(x.right, key);
        else if (cmp > 0) return get(x.left, key);
        else return x.value;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) return new Node(key, value, 0);
        int cmp = x.key.compareTo(key);
        if (cmp < 0) x.right = put(x.right, key, value);
        else if (cmp > 0) x.left = put(x.left, key, value);
        else x.value = value;
        x.N = size(x.right) + size(x.left) + 1;
        return x;
    }

    public Key max(){
        return max(root).key;
    }

    private Node max(Node node){
        if (node.right == null) return node;
        return max(node.right);
    }

    public Node min(){
//        Node currentNode = root;
//        while (currentNode.left != null)
//            currentNode = currentNode.left;
//        return currentNode;
        min(root);
    }

    private Node min(Node node){
        if (node.left == null) return node;
        return min(node.left);
    }

    public Key floor(Key key){
        return floor(root, key);
    }

    /*
     * floor表示查找小于给定key的键值中最大的一个
     * 当key小于node.key时, 小于key的最大节点值必然在当前节点的左子树中
     *
     * 当key大于node.key时，有以下两种可能
     * 1. 当前节点就是小于key的最大值，其右子树的所有节点都大于key
     * 2. floor（key）在当前节点的右子树中
     */
    private Key floor(Node node, Key key){
        if (node == null) return null;
        int cmp = node.key.compareTo(key);
        if       (cmp > 0)  return floor(node.left, key);
        else if  (cmp == 0) return node.key;
        else{
            Key rs = floor(node.right, key);
            if   (rs == null)  return node.key;
            else               return rs;
        }
    }

    // 查找大于key的最小值
    public Key celling(Key key){
        // TODO: 非递归的celling函数在部分情况无法获得正确的值
        Node cur = root;
        while (cur != null){
            int cmp = cur.key.compareTo(key);
            if       (cmp == 0)     return cur.key;
            else if  (cmp < 0)      cur = cur.right;
            else{
               if (cur.left != null && cur.left.key.compareTo(key) >= 0)  cur = cur.left;
               else                                                       return cur.key;
            }
        }
        return null;
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

    public void deleteMin(){
        deleteMin(root);
    }

    private Node deleteMin(Node node){
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public Node delete(Node node, Key key){
        if (node == null)  return null;
        int cmp = node.key.compareTo(key);
        if      (cmp < 0)   node.right = delete(node.right, key);
        else if (cmp > 0)   node.left = delete(node.left, key);
        else{
            if (node.left == null)  return node.right;
            if (node.right == null) return node.left;
            Node temp = node;
            node = min(node.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public String inOrder(){
        StringBuilder builder = new StringBuilder();
        inOrder(root, builder);
        return builder.toString();
    }

    private void inOrder(Node node, StringBuilder builder){
        if (node == null)  return;
        inOrder(node.left, builder);
        builder.append(node.value + " ");
        inOrder(node.right, builder);
    }
}