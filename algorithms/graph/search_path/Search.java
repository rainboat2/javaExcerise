package graph.search_path;

/**
 * 实现search接口的类应该接受一个Graph对象和一个顶点（int）s
 * 对s在图g中所有的连通点进行查找
 */
public interface Search {

    /**
     * @return 该顶点v与s是否连通
     * @param v 顶点
     */
    boolean isMarked(int v);

    /**
     * @return 返回图g中与顶点s连通的点的个数
     */
    int count();
}
