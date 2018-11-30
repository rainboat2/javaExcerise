package graph.most_small_tree;

import graph.graphs.Edge;

public interface MST {

    /**
     * @return 最小生成树中的所有边
     */
    Iterable<Edge> edges();

    /**
     * @return 最小生成树的总权值
     */
    double weight();
}
