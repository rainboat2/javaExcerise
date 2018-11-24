package graph.search_path;

/**
 * 实现 path接口的类应该接受一个 Graph对象和一个顶点（int）s
 * 查找从 s到图 g中任意一点的路径
 */
public interface Path {

    /**
     * @param v 图中任意一个点
     * @return 从 s到图中任意一点 v的路径是否存在
     */
    boolean hasPathTo(int v);

    /**
     * @param v 图中任意一个顶点
     * @return 从 s到图中任意一点 v的路径
     */
    Iterable<Integer> pathTo(int v);
}
