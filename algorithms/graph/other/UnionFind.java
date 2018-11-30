package graph.other;

public class UnionFind {

    public static void main(String[] args){
        UnionFind uf = new UnionFind(10);
        int[] v = new int[]{1, 3, 5, 7, 1, 5};
        int[] w = new int[]{2, 4, 6, 8, 3, 7};
        for (int i = 0; i < v.length; i++)
            uf.union(v[i], w[i]);
        for (int i = 0; i <= 4; i++)
            uf.isConnected(i, i+5);
        System.out.println();
    }

    private int[] id;
    private int count;   // 连通分量的数量

    public UnionFind(int v){
        id = new int[v];
        for (int i = 0; i < v; i++)
            id[i] = i;
        count = v;
    }

    public boolean isConnected(int v, int w){
        return find(v) == find(w);
    }

    public void union(int v, int w){
        int vRoot = find(v);
        int wRoot = find(w);
        if (vRoot == wRoot) return;
        id[vRoot] = wRoot;   // 将v树连接到w节点上去
        count--;
    }

    public int count(){
        return count;
    }

    private int find(int v){
        int p = v;
        while (!isRoot(p)) p = id[p]; // 找到节点v对应的根节点p
        while (!isRoot(v)){ //将查找路径上所有节点的父节点直接设为p
            int temp = v;
            v = id[v];
            id[temp] = p;
        }
        return p;
    }

    private boolean isRoot(int v){
        return v == id[v];
    }
}
